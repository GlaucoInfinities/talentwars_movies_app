package au.com.talentwars.ui.details

import android.net.Uri
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import au.com.talentwars.R
import au.com.talentwars.data.model.Genres
import au.com.talentwars.data.model.Movies
import au.com.talentwars.ui.RatedMoviesUiState
import au.com.talentwars.ui.components.ButtonBack
import au.com.talentwars.ui.components.CachedAsyncImage
import au.com.talentwars.ui.components.CenteredCircularProgressIndicator
import au.com.talentwars.ui.components.FavouritesStar
import au.com.talentwars.ui.components.ImageCardRoundedTopEnd
import au.com.talentwars.ui.components.Screen
import au.com.talentwars.ui.components.SeekBar
import au.com.talentwars.ui.components.TextInterBold
import au.com.talentwars.ui.components.TextInterRegular
import au.com.talentwars.ui.components.TextJomhuriaRegular
import au.com.talentwars.ui.popular.PopularMoviesViewModel
import com.google.gson.Gson
import kotlin.math.roundToInt

@Composable
fun DetailsMoviesScreen(
    navController: NavHostController,
    movie: Movies
) {

    val popularMoviesViewModel: PopularMoviesViewModel = hiltViewModel()
    val detailsMoviesViewModel: DetailsMoviesViewModel = hiltViewModel()
    detailsMoviesViewModel.setMovie(movie)


    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            Modifier
                .background(Color.White),
        ) {
            Column {
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
                        // RateClickable(navController)
                        ClickableContainer(navController, movie)
                        OverviewContainer(movie)
                    }
                }

            }
            val modalVisible by detailsMoviesViewModel.modalVisible.collectAsState()
            if (modalVisible) {
                BottomSheetContent(
                    onClose = { detailsMoviesViewModel.hideModal() },
                    visible = modalVisible,
                    detailsMoviesViewModel = detailsMoviesViewModel,
                    navController,
                    movie
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetContent(
    onClose: () -> Unit,
    visible: Boolean,
    detailsMoviesViewModel: DetailsMoviesViewModel,
    navController: NavHostController,
    movie: Movies
) {

    val animatableHeight = remember { Animatable(if (visible) 200f else 0f) }

    val uiState by detailsMoviesViewModel.uiStateRatedMovie.collectAsState(initial = RatedMoviesUiState.Initial)

    LaunchedEffect(visible) {
        animatableHeight.animateTo(
            if (visible) 200f else 0f,
            animationSpec = tween(durationMillis = 300)
        )
    }

    ModalBottomSheetLayout(
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val numbers = mutableListOf<Double>()
                var number = 1.0
                while (number <= 10.0) {
                    numbers.add(number)
                    number += 0.5
                }

                DecimalSlider(detailsMoviesViewModel)

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Button(
                        onClick = {
                            detailsMoviesViewModel.hideModal()
                            detailsMoviesViewModel.onButtonClick()
                            val jsonArgs = Uri.encode(Gson().toJson(movie))
                            navController.navigate(
                                route = Screen.RatedScreen.route + "/$jsonArgs"
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Black
                        )
                    ) {
                        TextInterBold(
                            text = "Rate",
                            color = colorResource(id = R.color.text_bottom_rated)
                        )
                    }
                }


                when (uiState) {
                    RatedMoviesUiState.Initial -> {}
                    RatedMoviesUiState.Loading -> {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.padding(all = 8.dp)
                        ) {
                            CenteredCircularProgressIndicator()
                        }
                    }

                    is RatedMoviesUiState.Success -> {}

                    is RatedMoviesUiState.Error -> {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.padding(all = 8.dp)
                        ) {
                            Text(
                                (uiState as RatedMoviesUiState.Error).errorMessage,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        },
        sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Expanded),
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.8f))
                .padding(16.dp)
        )
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
            ) {
                ImageCardRoundedTopEnd(
                    urlImage = movie.poster_path,
                )
            }
            Column(
                modifier = Modifier
                    .padding(5.dp, 0.dp, 0.dp, 8.dp)
                    .weight(1f)
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
    val topButtonTextColor by detailsMoviesViewModel.topButtonTextColor.observeAsState()

    val buttonTopText by detailsMoviesViewModel.buttonTopText.observeAsState()
    val buttonBottomText by detailsMoviesViewModel.buttonBottomText.observeAsState()

    Row(
        verticalAlignment = Alignment.Top
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .clickable {
                    detailsMoviesViewModel.showModal()
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
                        color = colorResource(id = topButtonTextColor!!)
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
                FavouritesStar(
                    modifier = Modifier.padding(start = 152.dp),
                    movie = movie
                )
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
fun DecimalSlider(detailsMoviesViewModel: DetailsMoviesViewModel) {
    var sliderValue by remember { mutableStateOf(5.0) }

    Column {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            TextJomhuriaRegular(
                text = "%.1f".format(sliderValue),
                fontSize = 50.sp,
            )
        }
        Slider(
            value = sliderValue.toFloat(),
            onValueChange = { newValue ->
                val roundedValue = (newValue * 2).roundToInt() / 2.0
                sliderValue = roundedValue
                detailsMoviesViewModel.setSliderValue(roundedValue)
            },
            valueRange = 1f..10f,
            steps = 0,
            colors = SliderDefaults.colors(
                thumbColor = colorResource(id = R.color.green_top_bar_text),
                activeTrackColor = colorResource(id = R.color.green_top_bar),
                inactiveTrackColor = colorResource(id = R.color.background_slider)
            )

        )
    }
}