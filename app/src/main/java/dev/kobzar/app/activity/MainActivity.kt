package dev.kobzar.app.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.util.Consumer
import androidx.work.WorkManager
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import dagger.hilt.android.AndroidEntryPoint
import dev.kobzar.asteroidslist.AsteroidsListScreen
import dev.kobzar.dangernotify.DangerNotifyWorker
import dev.kobzar.onboarding.OnBoardingScreen
import dev.kobzar.platform.base.BaseActivity
import dev.kobzar.preferences.DataStoreManager
import dev.kobzar.ui.compose.AppTheme
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModels()

    @Inject lateinit var preferencesRepository: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent?.data?.let {
            Log.e("MainActivity", it.toString())
        }

        val isFirstStart = runBlocking { preferencesRepository.isFirstStart.first() }

//        val workerRequester = DangerNotifyWorker.createPeriodicRequester()
        val workerRequester = DangerNotifyWorker.createOneTimeRequester()
//
//        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
//            "app_worker",
//            ExistingPeriodicWorkPolicy.KEEP,
//            workerRequester
//        )

        WorkManager.getInstance(this).enqueue(workerRequester)

        setContent {
            AppTheme {
                val startScreen = if (isFirstStart) {
                    OnBoardingScreen()
                } else {
                    AsteroidsListScreen()
                }

                Navigator(screen = startScreen) { navigator ->
                    SlideTransition(navigator)
                    HandleIntent(this, navigator)
                }
            }
        }

    }
}

fun handleIntentAction(context: Context, intent: Intent, navigator: Navigator) {
    Log.e("MainActivity", intent.data.toString())
    Toast.makeText(context, "${intent.data}", Toast.LENGTH_SHORT).show()
//    navigator.push(ScreenRegistry.get(SharedScreen.SettingsScreen))
}

@Composable
fun HandleIntent(context: Context, navigator: Navigator) {

    Log.e("MainActivity", "HandleIntent")

    LaunchedEffect(Unit) {
        callbackFlow<Intent> {
            val componentActivity = context as ComponentActivity
            val currentIntent = componentActivity.intent
            if (currentIntent?.data != null) {
                trySend(currentIntent)
            }
            val consumer = Consumer<Intent> { trySend(it) }
            componentActivity.addOnNewIntentListener(consumer)
            awaitClose { componentActivity.removeOnNewIntentListener(consumer) }
        }.collectLatest { handleIntentAction(context, it, navigator) }
    }
}