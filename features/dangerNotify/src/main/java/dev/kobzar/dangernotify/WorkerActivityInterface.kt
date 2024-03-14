package dev.kobzar.dangernotify

import android.app.Activity

interface WorkerActivityInterface {
    val activityClass: Class<out Activity>
}