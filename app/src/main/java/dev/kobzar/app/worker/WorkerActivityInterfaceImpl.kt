package dev.kobzar.app.worker

import dev.kobzar.app.activity.MainActivity
import dev.kobzar.dangernotify.WorkerActivityInterface

class WorkerActivityInterfaceImpl: WorkerActivityInterface {
    override val activityClass = MainActivity::class.java
}