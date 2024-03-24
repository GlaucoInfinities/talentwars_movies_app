package au.com.talentwars.ui.popular

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import au.com.talentwars.R
import au.com.talentwars.ui.PopularMoviesUiState
import au.com.talentwars.ui.components.CenteredCircularProgressIndicator
import au.com.talentwars.ui.components.Screen
import au.com.talentwars.ui.components.TextFieldInterBold
import au.com.talentwars.ui.components.TextInterRegular
import au.com.talentwars.ui.components.TextJomhuriaRegular
import au.com.talentwars.ui.popular.components.PopularMovieCompose
import com.google.gson.Gson

@Composable
fun PopularMoviesScreen(navController: NavHostController) {
    val viewModel: PopularMoviesViewModel = hiltViewModel()

    var searchText by remember { viewModel.searchText }
    val titleText by remember { viewModel.titleText }

    LaunchedEffect(searchText.text) {
        viewModel.updateTitleText()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.green_top_bar))
                .height(215.dp)
                .padding(top = 57.dp)
                .padding(horizontal = 31.dp)

        ) {
            TextFieldInterBold(searchText = searchText, setSearchText = { searchText = it })

            TextJomhuriaRegular(
                text = titleText, fontSize = 60.sp,
                color = colorResource(id = R.color.green_top_bar_text),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 36.dp)
            )
        }
        InitLoadingPage(viewModel,searchText,navController)
    }
}
@Composable
fun InitLoadingPage(
    viewModel: PopularMoviesViewModel,
    searchText: TextFieldValue,
    navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsState(initial = PopularMoviesUiState.Initial)

    when (uiState) {
        PopularMoviesUiState.Initial -> {}
        PopularMoviesUiState.Loading -> {
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier.padding(all = 8.dp)
            ) {
                CenteredCircularProgressIndicator()
            }
        }

        is PopularMoviesUiState.Success -> {
            ListMovies(uiState, searchText = searchText, navController)
        }

        is PopularMoviesUiState.Error -> {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(all = 8.dp)) {
                Text(
                    (uiState as PopularMoviesUiState.Error).errorMessage,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
@Composable
fun ListMovies(
    uiState: PopularMoviesUiState,
    searchText: TextFieldValue, navController: NavHostController
) {
    val viewModel: PopularMoviesViewModel = hiltViewModel()
    val list = (uiState as PopularMoviesUiState.Success).movies

    LazyColumn(
        modifier = Modifier.padding(top = 10.dp, bottom = 5.dp),
        ) {
        itemsIndexed(list.filter {
            it.title.contains(
                searchText.text,
                ignoreCase = true
            )
        }) { index, movie ->
            PopularMovieCompose(
                movie
            ) {
                val jsonArgs = Uri.encode(Gson().toJson(movie))
                navController.navigate(
                    route = Screen.DetailScreen.route + "/$jsonArgs"
                )
            }
            if (index == list.size - 1 && !viewModel.isLoading) {
                Button(
                    onClick = {
                        viewModel.loadNextPage()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.green_top_bar)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(9.dp)
                ) {
                    TextInterRegular(
                        text = "Load More",
                        color = colorResource(id = R.color.green_top_bar_text),
                    )
                }
            }
        }
    }
}
