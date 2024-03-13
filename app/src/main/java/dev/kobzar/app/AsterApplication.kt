package dev.kobzar.app

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import cafe.adriel.voyager.core.registry.ScreenRegistry
import dagger.hilt.android.HiltAndroidApp
import dev.kobzar.asteroidslist.AsteroidsListScreen
import dev.kobzar.compare.CompareScreen
import dev.kobzar.dangernotify.DangerNotifyWorker
import dev.kobzar.details.DetailsScreen
import dev.kobzar.favorites.FavoritesScreen
import dev.kobzar.navigation.shared.SharedScreen
import dev.kobzar.onboarding.OnBoardingScreen
import dev.kobzar.repository.AsteroidDetailsRepository
import dev.kobzar.settings.SettingsScreen
import dev.shreyaspatil.permissionFlow.PermissionFlow
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors
import javax.inject.Inject

@HiltAndroidApp
class AsterApplication : Application(), Configuration.Provider  {

    @Inject
    lateinit var workerFactory: AppWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()

//        val permissionDispatcher = Executors.newFixedThreadPool(3).asCoroutineDispatcher()
//        PermissionFlow.init(this, permissionDispatcher)
//        PermissionFlow.getInstance().startListening()

        ScreenRegistry {
            register<SharedScreen.OnBoardingScreen> { OnBoardingScreen() }
            register<SharedScreen.AsteroidsListScreen> { AsteroidsListScreen() }
            register<SharedScreen.DetailsScreen> { DetailsScreen(asteroidId = it.asteroidId) }
            register<SharedScreen.FavoritesScreen> { FavoritesScreen() }
            register<SharedScreen.SettingsScreen> { SettingsScreen() }
            register<SharedScreen.CompareScreen> { CompareScreen() }
        }

    }

}

class AppWorkerFactory @Inject constructor(
    private val asteroidDetailsRepository: AsteroidDetailsRepository,
) :
    WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {
        return DangerNotifyWorker(
            appContext,
            workerParameters,
            asteroidDetailsRepository
        )
    }

}
