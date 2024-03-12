package dev.kobzar.dangernotify

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.net.Uri
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dev.kobzar.repository.AsteroidDetailsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

const val APP_WORKER_TAG = "AppWorker"

@HiltWorker
class DangerNotifyWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val asteroidDetailsRepository: AsteroidDetailsRepository
) : CoroutineWorker(appContext, workerParams) {

    private val channelId = "kobzar_notification_channel"

    override suspend fun doWork(): Result {

        Log.d("DangerNotifyWorker", "doWork")

        return Result.success()
    }

    private fun pendingIntent(id: String?): PendingIntent {
        val deepLink = "nextgen://detailsScreenFragment/$id"
        val uri = Uri.parse(deepLink)

        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage(appContext.packageName)

        return PendingIntent.getActivity(appContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }


    private fun sendNotification(title: String, message: String, id: String?) {
        val builder = NotificationCompat.Builder(appContext, channelId)
            .setSmallIcon(androidx.constraintlayout.widget.R.drawable.notification_bg)
            .setContentTitle(title)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(message)
            )
            .setContentIntent(pendingIntent(id))
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

    private fun getRandomNotifyID(): Int {
        return System.currentTimeMillis().toInt()
    }

    companion object {

        private val constraints = Constraints.Builder()
            .setRequiredNetworkType(androidx.work.NetworkType.CONNECTED)
            .build()

        fun createPeriodicRequester(): PeriodicWorkRequest {

            return PeriodicWorkRequestBuilder<DangerNotifyWorker>(1, TimeUnit.HOURS)
                .setConstraints(constraints)
                .addTag(APP_WORKER_TAG)
                .build()
        }

    }

}
