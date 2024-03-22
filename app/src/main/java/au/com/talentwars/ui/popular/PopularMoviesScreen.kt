package au.com.talentwars.ui.popular

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import au.com.talentwars.R
import au.com.talentwars.ui.PopularMoviesUiState
import au.com.talentwars.ui.components.PopularMovieCompose
import au.com.talentwars.ui.components.Screen
import com.google.gson.Gson

@Composable
fun PopularMoviesScreen(navController: NavHostController) {
    val viewModel: PopularMoviesViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState(initial = PopularMoviesUiState.Initial)
    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    val titleText by remember {
        derivedStateOf {
            if (searchText.text.isEmpty()) {
                "Popular Right Now"
            } else {
                "Your Results"
            }
        }
    }

    val searchBarModifier = Modifier
        .fillMaxWidth()
        .background(color = colorResource(id = R.color.green_top_bar))

    val fontFamily = FontFamily(Font(R.font.inter_bold))
    val textStyle = TextStyle(
        fontFamily = fontFamily,
        fontSize = 16.sp,
        color = colorResource(id = R.color.black),
    )
    val green = colorResource(id = R.color.green_top_bar)

    Column(modifier = Modifier.fillMaxSize()) {
        Column(searchBarModifier) {
            TextField(
                value = searchText,
                onValueChange = { newText ->
                    searchText = newText
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                ),
                textStyle = textStyle,
                placeholder = {
                    Text(
                        "Search", style = textStyle
                    )
                },
                trailingIcon = {
                    if (searchText.text.isNotEmpty()) {
                        ClearIcon(onClearClicked = { searchText = TextFieldValue("") })
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 57.dp)
                    .padding(horizontal = 37.dp)
                    .clip(shape = RoundedCornerShape(37.dp))
                    .height(50.dp)
                    .wrapContentHeight(align = Alignment.CenterVertically),
            )

            TitleText(text = titleText)

        }
        when (uiState) {
            PopularMoviesUiState.Initial -> {
                //Nothing to show
            }

            PopularMoviesUiState.Loading -> {
                Box(
                    contentAlignment = Alignment.Center, modifier = Modifier.padding(all = 8.dp)
                ) {
                    CenteredCircularProgressIndicator()
                }
            }

            is PopularMoviesUiState.Success -> {
                val list = (uiState as PopularMoviesUiState.Success).movies
                LazyColumn(
                    modifier = Modifier.padding(top = 10.dp),
                    contentPadding = PaddingValues(bottom = 5.dp)
                ) {
                    itemsIndexed(list.filter {
                        it.title.contains(
                            searchText.text,
                            ignoreCase = true
                        )
                    }) { index, movie ->
                        PopularMovieCompose(
                            movie,
                        ) {
                            val jsonArgs = Uri.encode(Gson().toJson(movie))
                            navController.navigate(
                                route = Screen.DetailScreen.route + "/false/${movie.title}/$jsonArgs"
                            )
                        }
                        if (index == list.size - 1 && !viewModel.isLoading) {
                            Button(
                                onClick = {
                                    viewModel.loadNextPage()
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = green),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(9.dp)
                            ) {
                                Text(
                                    text = "Load More",
                                    style = textStyle,
                                    color = colorResource(id = R.color.green_top_bar_text),
                                )
                            }
                        }
                    }
                }
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
}

@Composable
fun ClearIcon(onClearClicked: () -> Unit) {
    Icon(
        painter = painterResource(id = R.drawable.ic_close_black_24),
        contentDescription = "Clear text",
        tint = Color.Gray,
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClearClicked() },
    )
}

@Composable
fun TitleText(text: String) {
    val fontFamily = FontFamily(Font(R.font.jomhuria_regular))
    val textStyle = TextStyle(
        fontFamily = fontFamily,
        fontSize = 60.sp,
        color = colorResource(id = R.color.green_top_bar_text),
    )
    Text(
        text = text, style = textStyle, modifier = Modifier
            .fillMaxWidth()
            .padding(top = 37.dp)
            .padding(bottom = 27.dp)
            .padding(horizontal = 34.dp)
    )
}

@Composable
fun CenteredCircularProgressIndicator() = CircularProgressIndicator(
    Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)
)