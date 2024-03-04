package dev.kobzar.impl.useCases

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.kobzar.domain.useCases.ConfigureFirstStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class ConfigureFirstStartImpl @Inject constructor(
    @ApplicationContext private val appContext: Context
): ConfigureFirstStart {

    private val configuredFile by lazy {
        File(appContext.filesDir, "configured")
    }

    override suspend fun invoke() {
        withContext(Dispatchers.IO) {
            configuredFile.createNewFile()
        }
    }
}
