package dev.kobzar.app.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import dagger.hilt.android.AndroidEntryPoint
import dev.kobzar.asteroidslist.AsteroidsListScreen
import dev.kobzar.dangernotify.DangerNotifyWorker
import dev.kobzar.details.DetailsScreen
import dev.kobzar.onboarding.OnBoardingScreen
import dev.kobzar.platform.base.BaseActivity
import dev.kobzar.preferences.DataStoreManager
import dev.kobzar.ui.compose.AppTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var preferencesRepository: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isFirstStart = runBlocking { preferencesRepository.isFirstStart.first() }

//        val workerRequester = DangerNotifyWorker.createPeriodicRequester()
        val workerRequester: OneTimeWorkRequest = DangerNotifyWorker.createOneTimeRequester()
//
//        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
//            "app_worker",
//            ExistingPeriodicWorkPolicy.KEEP,
//            workerRequester
//        )

        WorkManager.getInstance(this).enqueueUniqueWork(
            "workName",
            ExistingWorkPolicy.REPLACE,
            OneTimeWorkRequestBuilder<DangerNotifyWorker>().build()
        )

        setContent {

            val data = intent?.getStringExtra("asteroidId")

            AppTheme {
                val startScreen = if (isFirstStart) {
                    OnBoardingScreen()
                } else {
                    AsteroidsListScreen()
                }

                if (data != null && !isFirstStart) {
                    viewModel.deleteNotifiedAsteroid(data)
                    Navigator(screens = listOf(AsteroidsListScreen(), DetailsScreen(data))) { navigator ->
                        SlideTransition(navigator)
                    }
                } else {
                    Navigator(screen = startScreen) { navigator ->
                        SlideTransition(navigator)
                    }
                }

            }
        }

    }
}
