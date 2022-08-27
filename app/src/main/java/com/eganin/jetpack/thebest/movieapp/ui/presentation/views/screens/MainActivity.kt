package com.eganin.jetpack.thebest.movieapp.ui.presentation.views.screens

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.work.WorkManager
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.application.MovieApp
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.list.TypeMovies
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.screens.detail.MovieDetails
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.screens.list.ListMovies
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.screens.search.ListMoviesSearch
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.screens.settings.SettingsScreen
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.theme.*

class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val isDarkModeValue = true
            val currentStyle = remember { mutableStateOf(JetMovieStyle.Orange) }
            val currentFontSize = remember { mutableStateOf(JetMovieSize.Medium) }
            val currentPaddingSize = remember { mutableStateOf(JetMovieSize.Medium) }
            val currentCornersStyle = remember { mutableStateOf(JetMovieCorners.Rounded) }
            val isDarkMode = remember { mutableStateOf(isDarkModeValue) }

            //запускаем Work manager
            StartWorker()
            val navController: NavHostController = rememberNavController()
            val bottomItems =
                listOf(
                    TypeMovies.POPULAR.value,
                    TypeMovies.TOP_RATED.value,
                    TypeMovies.NOW_PLAYING.value,
                    TypeMovies.UP_COMING.value,
                    TypeMovies.SEARCH.value,
                    "settings",
                )

            // root uri for deep links
            val uri = "https://android.movieapp"
            // scaffoldState for SnackBar
            val scaffoldState = rememberScaffoldState()
            MainTheme(
                style = currentStyle.value,
                darkTheme = isDarkMode.value,
                textSize = currentFontSize.value,
                corners = currentCornersStyle.value,
                paddingSize = currentPaddingSize.value
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        scaffoldState = scaffoldState,
                        bottomBar = {
                            BottomNavigation(
                                backgroundColor = BackgroundColor
                            ) {
                                bottomItems.forEach { screen ->
                                    BottomNavigationItem(selected = false,
                                        onClick = { navController.navigate(screen) },
                                        icon = { GetIcon(screen = screen) })
                                }
                            }
                        }
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = TypeMovies.POPULAR.value
                        ) {
                            // handle deep links
                            composable(
                                route = "/movies/{id}",
                                deepLinks = listOf(navDeepLink { uriPattern = "$uri/movies/{id}" })
                            ) { navBackStackEntry ->
                                OpenDetailsPage(
                                    navController = navController,
                                    scaffoldState = scaffoldState,
                                    movieId = navBackStackEntry.arguments?.get("id").toString()
                                        .toInt()
                                )
                            }
                            composable(TypeMovies.POPULAR.value) {
                                ListMovies(
                                    typeMovie = TypeMovies.POPULAR,
                                    navController = navController,
                                )
                            }
                            composable(TypeMovies.TOP_RATED.value) {
                                ListMovies(
                                    typeMovie = TypeMovies.TOP_RATED,
                                    navController = navController,
                                )
                            }
                            composable(TypeMovies.NOW_PLAYING.value) {
                                ListMovies(
                                    typeMovie = TypeMovies.NOW_PLAYING,
                                    navController = navController,
                                )
                            }
                            composable(TypeMovies.UP_COMING.value) {
                                ListMovies(
                                    typeMovie = TypeMovies.UP_COMING,
                                    navController = navController,
                                )
                            }
                            composable(TypeMovies.SEARCH.value) {
                                ListMoviesSearch(
                                    navController = navController,
                                )
                            }
                            composable("details") {
                                OpenDetailsPage(
                                    navController = navController,
                                    scaffoldState = scaffoldState,
                                )
                            }
                            composable("settings") {
                                SettingsScreen(
                                    isDarkMode = isDarkMode.value,
                                    currentTextSize = currentFontSize.value,
                                    currentPaddingSize = currentPaddingSize.value,
                                    currentCornersStyle = currentCornersStyle.value,
                                    onDarkModeChanged = {
                                        isDarkMode.value = it
                                    },
                                    onNewStyle = {
                                        currentStyle.value = it
                                    },
                                    onTextSizeChanged = {
                                        currentFontSize.value = it
                                    },
                                    onPaddingSizeChanged = {
                                        currentPaddingSize.value = it
                                    },
                                    onCornersStyleChanged = {
                                        currentCornersStyle.value = it
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun OpenDetailsPage(
    navController: NavController,
    scaffoldState: ScaffoldState,
    movieId: Int? = null
) {
    if (movieId == null) {
        // получаем id из Back Stack и передаем на новый экран
        navController.previousBackStackEntry?.savedStateHandle?.get<Int>("ID_KEY")
            ?.let { id ->
                MovieDetails(
                    id = id,
                    scaffoldState = scaffoldState,
                    navController = navController
                )
            }
    } else {
        // launch from deep links
        MovieDetails(
            id = movieId,
            scaffoldState = scaffoldState,
            navController = navController
        )
    }

}

@Composable
private fun GetIcon(screen: String) {
    when (screen) {
        TypeMovies.POPULAR.value -> NavigationIcon(
            description = screen,
            idPainter = R.drawable.ic_format_list_bulleted_square
        )
        TypeMovies.TOP_RATED.value -> NavigationIcon(
            description = screen,
            idPainter = R.drawable.ic_calendar_star_outline
        )
        TypeMovies.NOW_PLAYING.value -> NavigationIcon(
            description = screen,
            idPainter = R.drawable.ic_animation_play_outline
        )
        TypeMovies.UP_COMING.value -> NavigationIcon(
            description = screen,
            idPainter = R.drawable.ic_timer_outline
        )
        TypeMovies.SEARCH.value -> NavigationIcon(
            description = screen,
            idPainter = R.drawable.ic_magnify_expand
        )
        "settings" -> NavigationIcon(
            description = "settings",
            idPainter = R.drawable.ic_baseline_settings_24,
        )
    }
}

@Composable
private fun NavigationIcon(description: String, idPainter: Int) {
    Icon(
        painter = painterResource(id = idPainter),
        contentDescription = description,
        tint = Black,
    )
}

@Composable
private fun StartWorker() {
    val repository =
        (LocalContext.current.applicationContext as MovieApp).myComponent.workerRepository
    WorkManager.getInstance(LocalContext.current.applicationContext).enqueue(repository.request)
}
