package dev.kobzar.dangernotify

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dev.kobzar.model.models.MainNotifiedModel
import dev.kobzar.repository.AsteroidDetailsRepository
import dev.kobzar.repository.DataStoreRepository
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
    private val asteroidDetailsRepository: AsteroidDetailsRepository,
    private val workerActivityInterface: WorkerActivityInterface,
    private val prefs: DataStoreRepository
) : CoroutineWorker(appContext, workerParams) {

    private val channelId = "asteroids_notification_channel"

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {

        val data =
            asteroidDetailsRepository.getAllAsteroidDetails().filter { it is UiState.Success }
                .first()

        val notifiedAsteroids = asteroidDetailsRepository.getNotifiedAsteroids().first()

        val prefs = prefs.getUserPreferences().first()

        val result = (data as UiState.Success)
        val currentTime = System.currentTimeMillis()
        val nextDayTime = if (prefs?.dangerNotifyPrefs?.checkIntervalHours != null) {
            currentTime + TimeUnit.HOURS.toMillis(prefs.dangerNotifyPrefs.checkIntervalHours.toLong())
        } else {
            currentTime + TimeUnit.DAYS.toMillis(1)
        }

        result.data.forEach {
            val closestApproach = it.closeApproachData.first { data ->
                data.epochDateCloseApproach >= currentTime
            }

            if (closestApproach.epochDateCloseApproach in (currentTime + 1)..<nextDayTime) {
                if (notifiedAsteroids.contains(MainNotifiedModel(it.id))) {
                    sendNotification(
                        title = it.name,
                        message = closestApproach.closeApproachDateFull,
                        id = it.id,
                        intent = createIntent(it.id)
                    )
                }
            }
        }

        Result.success()
    }

    private fun sendNotification(
        title: String,
        message: String,
        id: String,
        intent: PendingIntent
    ) {

        val builder = NotificationCompat.Builder(appContext, channelId)
            .setSmallIcon(androidx.constraintlayout.widget.R.drawable.notification_bg)
            .setContentTitle(title)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(message)
            )
            .setContentIntent(intent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(appContext)) {
            if (ActivityCompat.checkSelfPermission(
                    appContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(id.toInt(), builder.build())
        }
    }

    private fun createIntent(asteroidId: String): PendingIntent {
        val flags = PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        val intent = Intent(appContext, workerActivityInterface.activityClass).apply {
            putExtra("asteroidId", asteroidId)
        }

        return PendingIntent.getActivity(
            appContext.applicationContext,
            asteroidId.hashCode(),
            intent,
            flags
        )
    }

    companion object {

        private val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        fun createPeriodicRequester(repeatInterval: Long): PeriodicWorkRequest {

            return PeriodicWorkRequestBuilder<DangerNotifyWorker>(repeatInterval, TimeUnit.HOURS)
                .setConstraints(constraints)
                .addTag(APP_WORKER_TAG)
                .build()
        }

        fun createOneTimeRequester(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<DangerNotifyWorker>()
                .setConstraints(constraints)
                .addTag(APP_WORKER_TAG)
                .build()
        }


    }

}
