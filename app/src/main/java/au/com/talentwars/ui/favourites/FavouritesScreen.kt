package au.com.talentwars.ui.favourites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import au.com.talentwars.R
import au.com.talentwars.ui.FavouritesMoviesUiState
import au.com.talentwars.ui.PopularMoviesUiState
import au.com.talentwars.ui.components.CenteredCircularProgressIndicator
import au.com.talentwars.ui.components.ImageCardRoundedTopEnd
import au.com.talentwars.ui.components.RateStar
import au.com.talentwars.ui.components.TextInterRegular
import au.com.talentwars.ui.components.TextJomhuriaRegular
import au.com.talentwars.ui.popular.ListMovies

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

        TextJomhuriaRegular(
            text = "My favourites", fontSize = 96.sp,
            color = colorResource(id = R.color.green_top_bar_text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 37.dp)
                .padding(bottom = 27.dp)
                .padding(horizontal = 34.dp)
        )
        ListFavourites()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 63.dp)
            .padding(bottom = 99.dp),
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
fun ListFavourites() {
    val viewModel: FavouritesViewModel = hiltViewModel()
    val favourites by remember { viewModel.favourites }.collectAsState(initial = emptyList())

//33do
    LazyRow {
        items(favourites) { item ->
            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                ImageCardRoundedTopEnd(item.backdropPath)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .offset(x = (0).dp, y = (-25).dp)
                        .padding(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    RateStar(
                        onClick = { }, rated = true
                    )
                    TextInterRegular(text = "My Rating")
                    TextInterRegular(text = item.countRating.toString(), fontSize = 20.sp)
                }


            }
        }
    }
}

@Composable
fun ButtonBack(backStack: NavHostController) {
    val fontFamily = FontFamily(Font(R.font.inter_regular))
    val textStyle = TextStyle(
        fontFamily = fontFamily,
        fontSize = 10.sp,
        color = colorResource(id = R.color.white),
    )
    Row(modifier = Modifier.padding(top = 34.dp, start = 32.dp)) {
        Button(
            onClick = { backStack.popBackStack() },
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.White.copy(alpha = 0.3f),
            ),
            shape = RoundedCornerShape(60.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back_24),
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.size(10.dp),
            )
            Text(
                modifier = Modifier.padding(start = 10.dp, end = 5.dp),
                text = "Back",
                style = textStyle
            )
        }
    }
}