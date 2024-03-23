package au.com.talentwars.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import au.com.talentwars.R
import au.com.talentwars.data.model.Genres
import au.com.talentwars.data.model.Movies
import au.com.talentwars.ui.components.CachedAsyncImage
import au.com.talentwars.ui.components.ImageCardRoundedTopEnd
import au.com.talentwars.ui.components.RateStar
import au.com.talentwars.ui.components.Screen
import au.com.talentwars.ui.components.SeekBar
import au.com.talentwars.ui.components.TextInterBold
import au.com.talentwars.ui.components.TextInterRegular
import au.com.talentwars.ui.components.TextJomhuriaRegular
import au.com.talentwars.ui.favourites.FavouritesViewModel
import au.com.talentwars.ui.popular.PopularMoviesViewModel

@Composable
fun DetailsMoviesScreen(
    navController: NavHostController,
    movie: Movies
) {
    val viewModel: DetailsMoviesViewModel = hiltViewModel()
    viewModel.setMovie(movie)

    val popularMoviesViewModel: PopularMoviesViewModel = hiltViewModel()

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header(movie = movie, navController)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .offset(x = (0).dp, y = (-55).dp)
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
            ) {
                Column(
                    modifier = Modifier
                        .width(140.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(topEnd = 35.dp))
                            .width(140.dp)
                            .height(190.dp)
                            .background(Color.White),
                        ) {
                        ImageCardRoundedTopEnd(urlImage = movie.poster_path)
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(20.dp, 70.dp, 0.dp, 0.dp)
                        .align(alignment = Alignment.Bottom)
                )
                {
                    TextInterBold(
                        modifier = Modifier.padding(top = 6.dp),
                        text = movie.title,
                        maxLines = 1,
                    )
                    TextInterRegular(
                        modifier = Modifier.padding(top = 8.dp),
                        text = popularMoviesViewModel.getMovieYear(movie.release_date) ?: "",
                        fontSize = 12.sp,
                        color = colorResource(id = R.color.grey_200),
                    )
                    HorizontalDetailsGenres(popularMoviesViewModel.getMovieGenres(movie.genre_ids))
                    Spacer(modifier = Modifier.height(15.dp))
                    Row {
                        TextInterBold(
                            modifier = Modifier.padding(end = 7.dp),
                            text = "${popularMoviesViewModel.calculatePercent(movie.vote_average)}%",
                            fontSize = 20.sp,
                        )
                        TextInterRegular(
                            text = "user score",
                            fontSize = 12.sp,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                    SeekBar(
                        progress = popularMoviesViewModel.calculatePercent(movie.vote_average)
                            .toFloat()
                    )
                }
            }
            ClickableContainer(navController, movie)
            Column {
                TextInterBold("Overview", modifier = Modifier.padding(top = 33.dp, bottom = 14.dp))
                TextInterRegular(movie.overview)
            }
        }
    }
}

@Composable
fun ClickableContainer(navController: NavHostController, movie: Movies) {

    val favouritesViewModel: FavouritesViewModel = hiltViewModel()

    var topButtonColor by remember { mutableStateOf(R.color.background_top_rate) }
    val initialTopButtonColor = R.color.background_top_rate
    val toggledTopButtonColorResource = R.color.background_top_rate_pressed
    var buttonTopText by remember { mutableStateOf("Rate it myself >") }
    var buttonBottomText by remember { mutableStateOf("add personal rating") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 27.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .clickable {
                    topButtonColor =
                        if (topButtonColor == initialTopButtonColor) toggledTopButtonColorResource else initialTopButtonColor
                    buttonTopText =
                        if (buttonTopText == "Rate it myself >") "You’ve rated this 0" else "Rate it myself >"
                    buttonBottomText =
                        if (buttonBottomText == "add personal rating") "click to reset" else "add personal rating"

                    favouritesViewModel.saveFavourites(movie)

                }
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                        .background(color = colorResource(id = topButtonColor))
                ) {
                    TextInterRegular(
                        modifier = Modifier
                            .padding(6.dp)
                            .align(Alignment.Center),
                        text = buttonTopText,
                        color = Color.White
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                bottomStart = 10.dp,
                                bottomEnd = 10.dp
                            )
                        )
                        .background(color = Color.Black)

                ) {
                    TextInterRegular(
                        modifier = Modifier
                            .padding(6.dp)
                            .align(Alignment.Center),
                        text = buttonBottomText,
                        fontSize = 12.sp,
                        color = colorResource(id = R.color.text_bottom_rate)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    navController.navigate(
                        route = Screen.FavouritesScreen.route
                    )
                },
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = colorResource(id = R.color.beige_200),
                ),
                shape = RoundedCornerShape(60.dp),
                elevation = ButtonDefaults.elevation(defaultElevation = 8.dp)

            ) {
                TextInterRegular(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = "View Favs",
                    color = colorResource(id = R.color.goldenrod_400)
                )
            }
        }
    }
}

@Composable
fun HorizontalDetailsGenres(items: List<Genres>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom
    ) {
        items.take(2).forEach { text ->
            TextInterRegular(
                text = text.name,
                maxLines = 1,
                fontSize = 12.sp,
                color = colorResource(id = R.color.grey_200),
                modifier = Modifier
                    .padding(end = 12.dp)
                    .padding(horizontal = 0.dp, vertical = 3.dp)
            )
        }
    }
}

@Composable
fun Header(movie: Movies, navController: NavHostController) {
    Box(
    ) {
        CachedAsyncImage(
            url = "https://media.themoviedb.org/t/p/w1920_and_h800_multi_faces" + movie.backdrop_path,
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp),
            contentScale = ContentScale.FillHeight
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .background(Color.Black.copy(alpha = 0.8f))
        )
        Column {
            ButtonBack(navController)
            TextTitle(movieTitle = movie.title)
            RateStar(onClick = { },  modifier = Modifier.padding(start = 160.dp), rated = false)
        }
    }
}

@Composable
fun TextTitle(movieTitle: String) {
    TextJomhuriaRegular(
        modifier = Modifier
            .padding(horizontal = 32.dp)
            .padding(top = 20.dp),
        maxLines = 1,
        text = movieTitle,
        fontSize = 96.sp,
        color = colorResource(id = R.color.white)
    )
}

@Composable
fun ButtonBack(backStack: NavHostController) {
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
            TextInterRegular(
                modifier = Modifier.padding(start = 10.dp, end = 5.dp),
                text = "Back to Search",
                fontSize = 10.sp,
                color = colorResource(id = R.color.white)
            )
        }
    }
}
