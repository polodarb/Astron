package dev.kobzar.asteroidslist

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import dev.kobzar.navigation.shared.SharedScreen
import dev.kobzar.ui.compose.theme.AppTheme

class AsteroidsListScreen : Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current

        val context = LocalContext.current

        val detailsScreen = rememberScreen(SharedScreen.DetailsScreen(null))
        val settingsScreen = rememberScreen(SharedScreen.SettingsScreen)
        val favoritesScreen = rememberScreen(SharedScreen.FavoritesScreen)

        AsteroidsListScreenComposable(
            onFavoritesClick = {
                navigator?.push(favoritesScreen)
            },
            onDetailsClick = {
                navigator?.push(detailsScreen)
            },
            onFilterClick = {
                Toast.makeText(context, "Filter BS", Toast.LENGTH_SHORT).show()
            },
            onSettingsClick = {
                navigator?.push(settingsScreen)
            }
        )
    }

}

@Composable
private fun AsteroidsListScreenComposable(
    onFavoritesClick: () -> Unit,
    onDetailsClick: () -> Unit,
    onFilterClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Scaffold(
        containerColor = AppTheme.colors.background,
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.End
            ) {
                FloatingActionButton(
                    onClick = onFilterClick,
                    modifier = Modifier.padding(bottom = AppTheme.spaces.space20)
                ) {
                    Image(imageVector = Icons.Default.ShoppingCart, contentDescription = null)
                }
                ExtendedFloatingActionButton(
                    onClick = onFavoritesClick,
                    text = {
                        Text(text = "Favorites")
                    },
                    icon = {
                        Image(imageVector = Icons.Default.Favorite, contentDescription = null)
                    }
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
                .background(AppTheme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "AsteroidsListScreen()", style = AppTheme.typography.subtitle)
                Spacer(modifier = Modifier.height(64.dp))
                Button(onClick = onDetailsClick) {
                    Text(text = "to DetailsScreen")
                }
                Spacer(modifier = Modifier.height(32.dp))
                Button(onClick = onSettingsClick) {
                    Text(text = "to SettingsScreen")
                }
            }
        }
    }
}