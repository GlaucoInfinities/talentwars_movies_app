package au.com.talentwars.ui.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import au.com.talentwars.data.model.Movies
import au.com.talentwars.ui.details.DetailsMoviesScreen
import au.com.talentwars.ui.popular.PopularMoviesScreen
import au.com.talentwars.ui.saved.FavouritesScreen
import com.google.gson.Gson

@Composable
fun Navigation(startDestination: String) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = Screen.PopularMoviesScreen.route) {
            PopularMoviesScreen(navController)
        }
        composable(route = Screen.FavouritesScreen.route) {
            FavouritesScreen(navController)
        }
        composable(
            route = Screen.DetailScreen.route +
                    "/{${Arguments.MOVIE_KEY}}",
            arguments = listOf(
                navArgument(Arguments.MOVIE_KEY) {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { entry ->
            val argument = entry.arguments?.getString(Arguments.MOVIE_KEY)
            val movie = Gson().fromJson(argument, Movies::class.java)
            DetailsMoviesScreen(navController, movie)
        }
    }
}

object Arguments {
    const val MOVIE_KEY = "movie"
}

sealed class Screen(val route: String) {
    object PopularMoviesScreen : Screen("popular_movies_screen")
    object FavouritesScreen : Screen("favourites_screen")
    object DetailScreen : Screen("detail_screen")
}