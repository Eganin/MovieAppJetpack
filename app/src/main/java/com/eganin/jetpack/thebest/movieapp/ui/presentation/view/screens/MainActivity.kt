package com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.application.MovieApp
import com.eganin.jetpack.thebest.movieapp.di.AppComponent
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.details.MovieDetails
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.list.ListMovies
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.fragments.list.TypeMovies
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens.ui.theme.BackgroundColor
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens.ui.theme.Black
import com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens.ui.theme.MovieAppTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController: NavHostController = rememberNavController()
                    val bottomItems =
                        listOf(
                            TypeMovies.POPULAR.value,
                            TypeMovies.TOP_RATED.value,
                            TypeMovies.NOW_PLAYING.value,
                            TypeMovies.UP_COMING.value,
                            TypeMovies.SEARCH.value,
                            "details",
                        )
                    val appComponent =
                        (LocalContext.current.applicationContext as MovieApp).myComponent

                    Scaffold(
                        bottomBar = {
                            BottomNavigation(
                                backgroundColor = BackgroundColor
                            ) {
                                bottomItems.forEach { screen ->
                                    BottomNavigationItem(selected = false,
                                        onClick = { navController.navigate(screen) },
                                        icon = {
                                            when (screen) {
                                                TypeMovies.POPULAR.value -> NavigationIcon(
                                                    description = TypeMovies.POPULAR.value,
                                                    idPainter = R.drawable.ic_format_list_bulleted_square
                                                )
                                                TypeMovies.TOP_RATED.value -> NavigationIcon(
                                                    description = TypeMovies.TOP_RATED.value,
                                                    idPainter = R.drawable.ic_calendar_star_outline
                                                )
                                                TypeMovies.NOW_PLAYING.value -> NavigationIcon(
                                                    description = TypeMovies.NOW_PLAYING.value,
                                                    idPainter = R.drawable.ic_animation_play_outline
                                                )
                                                TypeMovies.UP_COMING.value -> NavigationIcon(
                                                    description = TypeMovies.UP_COMING.value,
                                                    idPainter = R.drawable.ic_timer_outline
                                                )
                                                TypeMovies.SEARCH.value -> NavigationIcon(
                                                    description = TypeMovies.SEARCH.value,
                                                    idPainter = R.drawable.ic_magnify_expand
                                                )

                                            }
                                        })
                                }
                            }
                        }
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = TypeMovies.POPULAR.value
                        ) {
                            composable(TypeMovies.POPULAR.value) {
                                CreateListMovie(
                                    typeMovies = TypeMovies.POPULAR,
                                    appComponent = appComponent,
                                    navController = navController,
                                )
                            }
                            composable(TypeMovies.TOP_RATED.value) {
                                CreateListMovie(
                                    typeMovies = TypeMovies.TOP_RATED,
                                    appComponent = appComponent,
                                    navController = navController,
                                )
                            }
                            composable(TypeMovies.NOW_PLAYING.value) {
                                CreateListMovie(
                                    typeMovies = TypeMovies.NOW_PLAYING,
                                    appComponent = appComponent,
                                    navController = navController,
                                )
                            }
                            composable(TypeMovies.UP_COMING.value) {
                                CreateListMovie(
                                    typeMovies = TypeMovies.UP_COMING,
                                    appComponent = appComponent,
                                    navController = navController,
                                )
                            }
                            composable(TypeMovies.SEARCH.value) {
                                CreateListMovie(
                                    typeMovies = TypeMovies.SEARCH,
                                    appComponent = appComponent,
                                    navController = navController,
                                )
                            }
                            composable("details"){
                                navController.previousBackStackEntry?.savedStateHandle?.get<Int>("ID_KEY")?.let {id->
                                    val scaffoldState = rememberScaffoldState()

                                    Scaffold(scaffoldState = scaffoldState) {
                                        MovieDetails(
                                            id = id,
                                            repository = appComponent.movieDetailsRepository,
                                            connection = appComponent.connection,
                                            scaffoldState = scaffoldState
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CreateListMovie(
    typeMovies: TypeMovies,
    appComponent: AppComponent,
    navController: NavController,
) {
    ListMovies(
        repository = appComponent.getMovieRepository(),
        isConnection = appComponent.connection,
        sharedPreferences = appComponent.getSharedPreferencesMovieType(),
        notificationsManager = appComponent.getNotificationManager(),
        typeMovie = typeMovies,
        navController = navController,
    )
}

@Composable
fun NavigationIcon(description: String, idPainter: Int) {
    Icon(
        painter = painterResource(id = idPainter),
        contentDescription = description,
        tint = Black,
    )
}
