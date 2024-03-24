package au.com.talentwars.ui.favourites

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import au.com.talentwars.R
import au.com.talentwars.ui.FavouritesMoviesUiState
import au.com.talentwars.ui.components.CenteredCircularProgressIndicator
import au.com.talentwars.ui.components.ImageCardRoundedTopEnd
import au.com.talentwars.ui.components.RateStar
import au.com.talentwars.ui.components.TextInterRegular
import au.com.talentwars.ui.components.TextJomhuriaRegular

@Composable
fun FavouritesScreen(navController: NavHostController) {

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxWidth()
                .size(width = 0.dp, height = 84.dp)
                .background(color = colorResource(id = R.color.green_top_bar))
        ) {
            ButtonBack(navController)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                TextJomhuriaRegular(
                    text = "My favourites", fontSize = 96.sp,
                    color = colorResource(id = R.color.green_top_bar_text),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 37.dp)
                        .padding(horizontal = 35.dp)
                )
                InitLoadingPage()

            }
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .fillMaxHeight()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color.White, Color.Transparent),
                            startX = Float.POSITIVE_INFINITY,
                            endX = 0f
                        )
                    )
                    .size(45.dp)
            )
            SearchForMore(navController)
        }
    }

}

@Composable
fun InitLoadingPage() {
    val viewModel: FavouritesViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState(initial = FavouritesMoviesUiState.Initial)

    when (uiState) {
        FavouritesMoviesUiState.Initial -> {}
        FavouritesMoviesUiState.Loading -> {
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier.padding(all = 8.dp)
            ) {
                CenteredCircularProgressIndicator()
            }
        }

        is FavouritesMoviesUiState.Success -> {
            ListFavourites(uiState)
        }

        is FavouritesMoviesUiState.Error -> {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(all = 8.dp)) {
                Text(
                    (uiState as FavouritesMoviesUiState.Error).errorMessage,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun SearchForMore(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 64.dp)
            .padding(bottom = 87.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                navController.popBackStack()
            },
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = colorResource(id = R.color.light_gray_100),
            ),
            shape = RoundedCornerShape(60.dp),
            elevation = ButtonDefaults.elevation(defaultElevation = 8.dp)
        ) {
            TextInterRegular(
                modifier = Modifier.padding(vertical = 10.dp),
                text = "Search for More",
                color = colorResource(id = R.color.black)
            )
        }
    }
}

@Composable
fun ListFavourites(uiState: FavouritesMoviesUiState) {
    val list = (uiState as FavouritesMoviesUiState.Success).favourites

    LazyRow(
        modifier = Modifier.padding(start = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(30.dp),
    ) {
        items(list) { item ->
            Column(
                Modifier
                    .background(Color.White)
                    .offset(x = (0).dp, y = (-9).dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ImageCardRoundedTopEnd(
                    item.poster_path,
                    modifier = Modifier
                        .width(120.dp)
                        .height(166.dp),
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .offset(x = (0).dp, y = (-30).dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    RateStar(
                        onClick = { }, rated = true
                    )
                    TextInterRegular(text = "My Rating", modifier = Modifier.padding(top = 5.dp))
                    TextInterRegular(
                        text = item.rating.toString(),
                        fontSize = 20.sp,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }


            }
        }
    }
}

@Composable
fun ButtonBack(backStack: NavHostController) {
    Row(
        modifier =
        Modifier.padding(top = 30.dp, start = 34.dp)
    ) {
        OutlinedButton(
            onClick = { backStack.popBackStack() },
            border = BorderStroke(0.dp, Color.Transparent),
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.White.copy(alpha = 0.3f),
                contentColor = Color.Transparent
            ),
            shape = RoundedCornerShape(60.dp),
            modifier = Modifier.height(28.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back_24),
                contentDescription = "Back",
                tint = colorResource(id = R.color.background_arrow_back),
                modifier = Modifier
                    .size(18.dp)
                    .padding(start = 5.dp),
            )
            TextInterRegular(
                text = "Back",
                fontSize = 10.sp,
                modifier = Modifier
                    .padding(start = 3.dp, end = 10.dp)
                    .background(Color.Transparent)
                // .padding(vertical = 7.dp),
            )
        }
    }
}