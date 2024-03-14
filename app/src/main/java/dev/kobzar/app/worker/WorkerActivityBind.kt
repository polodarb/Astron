package dev.kobzar.app.worker

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.kobzar.dangernotify.WorkerActivityInterface
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class WorkerActivityBind {

    @Provides
    @Singleton
    fun provideWorkerActivityInterface(): WorkerActivityInterface {
        return WorkerActivityInterfaceImpl() // Create the instance here
    }


}