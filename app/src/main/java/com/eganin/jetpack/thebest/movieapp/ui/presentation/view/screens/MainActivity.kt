package com.eganin.jetpack.thebest.movieapp.ui.presentation.view.screens

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eganin.jetpack.thebest.movieapp.R
import com.eganin.jetpack.thebest.movieapp.application.MovieApp
import com.eganin.jetpack.thebest.movieapp.di.AppComponent
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
                            TypeMovies.POPULAR,
                            TypeMovies.TOP_RATED,
                            TypeMovies.NOW_PLAYING,
                            TypeMovies.UP_COMING,
                            TypeMovies.SEARCH,
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
                                        onClick = { navController.navigate(screen.value) },
                                        icon = {
                                            when (screen) {
                                                TypeMovies.POPULAR -> NavigationIcon(
                                                    description = TypeMovies.POPULAR.value,
                                                    idPainter = R.drawable.ic_format_list_bulleted_square
                                                )
                                                TypeMovies.TOP_RATED -> NavigationIcon(
                                                    description = TypeMovies.TOP_RATED.value,
                                                    idPainter = R.drawable.ic_calendar_star_outline
                                                )
                                                TypeMovies.NOW_PLAYING -> NavigationIcon(
                                                    description = TypeMovies.NOW_PLAYING.value,
                                                    idPainter = R.drawable.ic_animation_play_outline
                                                )
                                                TypeMovies.UP_COMING -> NavigationIcon(
                                                    description = TypeMovies.UP_COMING.value,
                                                    idPainter = R.drawable.ic_timer_outline
                                                )
                                                TypeMovies.SEARCH -> NavigationIcon(
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
                                CreateListMovie(typeMovies = TypeMovies.POPULAR.value,appComponent=appComponent)
                            }
                            composable(TypeMovies.TOP_RATED.value) {
                                CreateListMovie(typeMovies = TypeMovies.TOP_RATED.value,appComponent=appComponent)
                            }
                            composable(TypeMovies.NOW_PLAYING.value) {
                                CreateListMovie(typeMovies = TypeMovies.NOW_PLAYING.value,appComponent=appComponent)
                            }
                            composable(TypeMovies.UP_COMING.value) {
                                CreateListMovie(typeMovies = TypeMovies.UP_COMING.value,appComponent=appComponent)
                            }
                            composable(TypeMovies.SEARCH.value) {
                                CreateListMovie(typeMovies = TypeMovies.SEARCH.value,appComponent=appComponent)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CreateListMovie(typeMovies: String,appComponent:AppComponent){
    ListMovies(
        repository = appComponent.getMovieRepository(),
        isConnection = appComponent.connection,
        sharedPreferences = appComponent.getSharedPreferencesMovieType(),
        notificationsManager = appComponent.getNotificationManager(),
        typeMovie = typeMovies,
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
