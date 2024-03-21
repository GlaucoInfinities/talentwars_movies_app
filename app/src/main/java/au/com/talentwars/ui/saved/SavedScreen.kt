package au.com.talentwars.ui.saved

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun SavedScreen(navController: NavHostController) {
    val viewModel: SavedViewModel = hiltViewModel()
}