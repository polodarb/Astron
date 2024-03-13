package dev.kobzar.dangernotify

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkRequest
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dev.kobzar.model.models.MainNotifiedModel
import dev.kobzar.repository.AsteroidDetailsRepository
import dev.kobzar.repository.uiStates.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

const val APP_WORKER_TAG = "AppWorker"

@HiltWorker
class DangerNotifyWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val asteroidDetailsRepository: AsteroidDetailsRepository
) : CoroutineWorker(appContext, workerParams) {

    private val channelId = "asteroids_notification_channel"

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {

        val data =
            asteroidDetailsRepository.getAllAsteroidDetails().filter { it is UiState.Success }
                .first()
        val result = (data as UiState.Success)
        val currentTime = System.currentTimeMillis()
        val nextDayTime = currentTime + TimeUnit.DAYS.toMillis(1)

        result.data.forEach {
            val closestApproach = it.closeApproachData.first { data ->
                data.epochDateCloseApproach >= currentTime
            }

            Log.d("DangerNotifyWorker", "Success: ${result.data.size}")

            if (closestApproach.epochDateCloseApproach in (currentTime + 1)..<nextDayTime) {
                asteroidDetailsRepository.getNotifiedAsteroids().collect { notify ->
                    if (notify.contains(MainNotifiedModel(it.id))) {
                        sendNotification(
                            title = it.name,
                            message = closestApproach.closeApproachDateFull,
                            id = it.id,
                            intent = createIntent(it.id)
                        )
                    }
                }
            }
        }

        Result.success()
    }

    private fun sendNotification(title: String, message: String, id: String, intent: PendingIntent) {

        val builder = NotificationCompat.Builder(appContext, channelId)
            .setSmallIcon(androidx.constraintlayout.widget.R.drawable.notification_bg)
            .setContentTitle(title)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(message)
            )
            .setContentIntent(intent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(appContext)) {
            if (ActivityCompat.checkSelfPermission(
                    appContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(getRandomNotifyID(), builder.build())
        }
    }

    private fun createIntent(asteroidId: String): PendingIntent {
        val flags = PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT

        return PendingIntent.getActivity(
            appContext.applicationContext,
            asteroidId.hashCode(),
            Intent(Intent.ACTION_VIEW, asteroidId.toUri()),
            flags
        )
    }

    private fun getRandomNotifyID(): Int {
        return System.currentTimeMillis().toInt()
    }

    companion object {

        private val constraints = Constraints.Builder()
//            .setRequiredNetworkType(androidx.work.NetworkType.CONNECTED)
            .build()

        fun createPeriodicRequester(): PeriodicWorkRequest {

            return PeriodicWorkRequestBuilder<DangerNotifyWorker>(1, TimeUnit.HOURS)
                .setConstraints(constraints)
                .addTag(APP_WORKER_TAG)
                .build()
        }

        fun createOneTimeRequester(): WorkRequest {
            return OneTimeWorkRequestBuilder<DangerNotifyWorker>()
                .setConstraints(constraints)
                .addTag(APP_WORKER_TAG)
                .build()
        }

    }

}
