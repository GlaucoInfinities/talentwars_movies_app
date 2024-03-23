package au.com.talentwars.ui.saved

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun FavouritesScreen(navController: NavHostController) {
    val viewModel: FavouritesViewModel = hiltViewModel()
    Column(modifier = Modifier.fillMaxSize()){
        Text(text = "My favourites")
    }
}