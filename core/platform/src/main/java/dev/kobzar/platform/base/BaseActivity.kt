package dev.kobzar.platform.base

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import dev.kobzar.platform.R
import java.time.Instant

open class BaseActivity : ComponentActivity() {

    private val channelId = "asteroids_notification_channel"
    private val notificationId = 1

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        installSplashScreen().apply {
            setOnExitAnimationListener { provider ->
                val passed = Instant.now().toEpochMilli() - provider.iconAnimationStartMillis
                val remaining = provider.iconAnimationDurationMillis - passed

                Handler(Looper.getMainLooper()).postDelayed(
                    { provider.remove() },
                    if (remaining > 0) remaining else 0
                )
            }
        }

        createNotificationChannel()
    }

    @SuppressLint("WrongConstant")
    private fun createNotificationChannel() {
        val name = "Compare error"
        val descriptionText = "Shows when attempting to compare less than two asteroids"
        val importance = NotificationManager.IMPORTANCE_MAX
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    open fun sendNotification(title: String, message: String? = null, autoCancel: Boolean = false) {
        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_MAX)

        if (autoCancel) {
            builder.setTimeoutAfter(7000)
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        NotificationManagerCompat.from(this).notify(notificationId, builder.build())
    }

}