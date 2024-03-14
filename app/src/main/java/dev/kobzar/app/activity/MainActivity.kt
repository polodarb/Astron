package dev.kobzar.app.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
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

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isFirstStart = runBlocking { preferencesRepository.isFirstStart.first() }

        firebaseAnalytics = Firebase.analytics

        val prefs = viewModel.prefsWorkerInterval.value

        val workerRequester = DangerNotifyWorker.createPeriodicRequester(
            repeatInterval = prefs ?: 1
        )

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "app_worker",
            ExistingPeriodicWorkPolicy.UPDATE,
            workerRequester
        )

        /* For testing OneTimeWorker

        val workerRequester: OneTimeWorkRequest = DangerNotifyWorker.createOneTimeRequester()

        WorkManager.getInstance(this).enqueueUniqueWork(
            "workName",
            ExistingWorkPolicy.REPLACE,
            OneTimeWorkRequestBuilder<DangerNotifyWorker>().build()
        )

         */

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
                    Navigator(
                        screens = listOf(
                            AsteroidsListScreen(), DetailsScreen(
                                asteroidId = data,
                                isDistanceDanger = true
                            )
                        )
                    ) { navigator ->
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
