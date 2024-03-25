package au.com.talentwars.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import au.com.talentwars.ui.components.FavouritesStar
import au.com.talentwars.ui.components.ImageCardRoundedTopEnd
import au.com.talentwars.ui.components.Screen
import au.com.talentwars.ui.components.SeekBar
import au.com.talentwars.ui.components.TextInterBold
import au.com.talentwars.ui.components.TextInterRegular
import au.com.talentwars.ui.components.TextJomhuriaRegular
import au.com.talentwars.ui.popular.PopularMoviesViewModel

@Composable
fun DetailsMoviesScreen(
    navController: NavHostController,
    movie: Movies
) {

    val popularMoviesViewModel: PopularMoviesViewModel = hiltViewModel()

    Column(
        Modifier
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Header(movie = movie, navController)

        Column(
            modifier = Modifier
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Top
        ) {
            DetailsContainer(movie, popularMoviesViewModel)

            Column(
                modifier = Modifier
                    .offset(x = (-0).dp, y = (-28).dp),
                verticalArrangement = Arrangement.Top
            )
            {
                ClickableContainer(navController, movie)
                OverviewContainer(movie)
            }
        }
    }
}

@Composable
fun DetailsContainer(movie: Movies, popularMoviesViewModel: PopularMoviesViewModel) {
    Column(
        modifier = Modifier
            .offset(x = (-0).dp, y = (-49).dp),
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
                        .width(131.dp)
                        .height(172.dp)
                        .background(Color.White),
                ) {
                    ImageCardRoundedTopEnd(
                        urlImage = movie.poster_path,
                        modifier = Modifier
                            .width(127.dp)
                            .height(168.dp)
                            .padding(top = 5.dp, start = 5.dp),
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(5.dp, 0.dp, 0.dp, 8.dp)
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
                    text = popularMoviesViewModel.getMovieYear(movie.release_date),
                    fontSize = 12.sp,
                    color = colorResource(id = R.color.grey_200),
                )
                HorizontalDetailsGenres(popularMoviesViewModel.getMovieGenres(movie.genre_ids))
                Spacer(modifier = Modifier.height(15.dp))
                Row {
                    TextInterBold(
                        modifier = Modifier.padding(end = 17.dp),
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

    }
}

@Composable
fun ClickableContainer(
    navController: NavHostController,
    movie: Movies
) {

    val detailsMoviesViewModel: DetailsMoviesViewModel = hiltViewModel()
    detailsMoviesViewModel.setMovie(movie)

    val topButtonColor by detailsMoviesViewModel.topButtonColor.observeAsState()
    val buttonTopText by detailsMoviesViewModel.buttonTopText.observeAsState()
    val buttonBottomText by detailsMoviesViewModel.buttonBottomText.observeAsState()

    Row(
        verticalAlignment = Alignment.Top
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .clickable {
                    detailsMoviesViewModel.onButtonClick()
                }
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                        .background(color = colorResource(id = topButtonColor!!))
                ) {
                    TextInterRegular(
                        modifier = Modifier
                            .padding(6.dp)
                            .align(Alignment.Center),
                        text = buttonTopText!!,
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
                        text = buttonBottomText!!,
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
                .height(245.dp),
            contentScale = ContentScale.FillHeight
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(245.dp)
                .background(Color.Black.copy(alpha = 0.8f))
        )
        Column {
            ButtonBack(navController)
            TextTitle(movieTitle = movie.title)
            Column(
                modifier = Modifier
                    .offset(x = (-0).dp, y = (-11).dp),
                verticalArrangement = Arrangement.Top
            ) {
                FavouritesStar(onClick = { }, modifier = Modifier.padding(start = 152.dp), movie=movie)
            }
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
fun OverviewContainer(movie: Movies) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        TextInterBold(
            "Overview",
            modifier = Modifier.padding(top = 33.dp, bottom = 14.dp)
        )
        TextInterRegular(movie.overview)
    }

}

@Composable
fun ButtonBack(backStack: NavHostController) {
    Row(
        modifier = Modifier.padding(top = 31.dp, start = 30.dp)
    ) {
        Button(
            onClick = { backStack.popBackStack() },
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.White.copy(alpha = 0.3f),
            ),
            modifier = Modifier.height(26.dp),
            shape = RoundedCornerShape(60.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back_24),
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier
                        .size(18.dp)
                        .padding(start = 9.dp),
                )
                TextInterRegular(
                    modifier = Modifier.padding(start = 8.dp, end = 9.dp),
                    text = "Back to Search",
                    fontSize = 10.sp,
                    color = colorResource(id = R.color.white)
                )
            }
        }
    }
}
