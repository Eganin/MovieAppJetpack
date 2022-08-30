package com.eganin.jetpack.thebest.movieapp.ui.presentation.views.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.*
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.work.WorkManager
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.application.MovieApp
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.workmanager.WorkerRepository
import com.eganin.jetpack.thebest.movieapp.domain.data.repositories.workmanager.WorkerRepositoryImpl
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.list.TypeMovies
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.screens.detail.MovieDetails
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.screens.list.ListMovies
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.screens.search.ListMoviesSearch
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.screens.settings.SettingsScreen
import com.eganin.jetpack.thebest.movieapp.ui.presentation.views.theme.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val isDarkModeValue = true
            val currentStyle = remember { mutableStateOf(JetMovieStyle.Orange) }
            val currentFontSize = remember { mutableStateOf(JetMovieSize.Medium) }
            val currentPaddingSize = remember { mutableStateOf(JetMovieSize.Small) }
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
                val systemUiController = rememberSystemUiController()
                val statusBarColor = JetMovieTheme.colors.cardBackground
                SideEffect {
                    // setup status bar
                    systemUiController.apply {
                        setSystemBarsColor(color = statusBarColor)
                    }
                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = JetMovieTheme.colors.primaryBackground,
                ) {
                    Scaffold(
                        scaffoldState = scaffoldState,
                        bottomBar = {
                            BottomNavigation(
                                backgroundColor = JetMovieTheme.colors.secondaryBackground,
                            ) {
                                bottomItems.forEach { screen ->
                                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                                    val isSelected = navBackStackEntry?.destination?.hierarchy
                                        ?.any { it.route == screen } == true
                                    BottomNavigationItem(
                                        selected = false,
                                        onClick = { navController.navigate(screen) },
                                        icon = {
                                            GetIcon(
                                                screen = screen,
                                                isSelected = isSelected,
                                            )
                                        })
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
private fun GetIcon(screen: String, isSelected: Boolean) {
    when (screen) {
        TypeMovies.POPULAR.value -> NavigationIcon(
            description = screen,
            idPainter = R.drawable.ic_format_list_bulleted_square,
            isSelected = isSelected,
        )
        TypeMovies.TOP_RATED.value -> NavigationIcon(
            description = screen,
            idPainter = R.drawable.ic_calendar_star_outline,
            isSelected = isSelected,
        )
        TypeMovies.NOW_PLAYING.value -> NavigationIcon(
            description = screen,
            idPainter = R.drawable.ic_animation_play_outline,
            isSelected = isSelected,
        )
        TypeMovies.UP_COMING.value -> NavigationIcon(
            description = screen,
            idPainter = R.drawable.ic_timer_outline,
            isSelected = isSelected,
        )
        TypeMovies.SEARCH.value -> NavigationIcon(
            description = screen,
            idPainter = R.drawable.ic_magnify_expand,
            isSelected = isSelected,
        )
        "settings" -> NavigationIcon(
            description = "settings",
            idPainter = R.drawable.ic_baseline_settings_24,
            isSelected = isSelected,
        )
    }
}

@Composable
private fun NavigationIcon(description: String, idPainter: Int, isSelected: Boolean) {
    Icon(
        painter = painterResource(id = idPainter),
        contentDescription = description,
        tint = if (isSelected) JetMovieTheme.colors.tintColor else JetMovieTheme.colors.secondaryText,
    )
}

@Composable
private fun StartWorker() {
    val workerRepository: WorkerRepository = WorkerRepositoryImpl()
    WorkManager.getInstance(LocalContext.current.applicationContext)
        .enqueue(workerRepository.request)
}
