package au.com.talentwars.ui.details

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import au.com.talentwars.R
import au.com.talentwars.data.model.Genres
import au.com.talentwars.data.model.Movies
import au.com.talentwars.ui.components.Screen
import au.com.talentwars.ui.components.SeekBar
import au.com.talentwars.ui.components.TextInterBold
import au.com.talentwars.ui.components.TextInterRegular
import au.com.talentwars.ui.popular.PopularMoviesViewModel
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest

@Composable
fun DetailsMoviesScreen(
    navController: NavHostController,
    movie: Movies
) {
    val viewModel: DetailsMoviesViewModel = hiltViewModel()
    viewModel.setMovie(movie)
    val context = LocalContext.current

    val imageLoader =
        remember { ImageLoader(context) }
    val request = ImageRequest.Builder(context)
        .data("https://media.themoviedb.org/t/p/w300_and_h450_bestv2" + movie.poster_path)
        .crossfade(true)
        .build()
    val fontFamilyBold = FontFamily(Font(R.font.inter_bold))
    val textStyleBold = TextStyle(
        fontFamily = fontFamilyBold,
        color = colorResource(id = R.color.black),
    )
    val fontFamilyRegular = FontFamily(Font(R.font.inter_regular))
    val textStyleRegular = TextStyle(
        fontFamily = fontFamilyRegular,
        fontSize = 12.sp,
        color = colorResource(id = R.color.black),
    )
    val lightGreyColor = colorResource(id = R.color.grey_200)
    val popularMoviesViewModel: PopularMoviesViewModel = hiltViewModel()

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header(context, movie = movie, navController)
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

                        SubcomposeAsyncImage(
                            model = request,
                            contentDescription = movie.title,
                            modifier = Modifier
                                .width(136.dp)
                                .height(188.dp)
                                .padding(start = 5.dp, top = 5.dp)
                                .clip(RoundedCornerShape(0.dp, 35.dp, 0.dp, 0.dp)),
                            contentScale = ContentScale.FillBounds,
                            imageLoader = imageLoader,
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(20.dp, 70.dp, 0.dp, 0.dp)
                        .align(alignment = Alignment.Bottom)
                )
                {
                    Text(
                        modifier = Modifier.padding(top = 6.dp),
                        text = movie.title,
                        style = textStyleBold,
                        fontSize = 16.sp,
                        maxLines = 1,
                        color = Color.Black
                    )
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = popularMoviesViewModel.getMovieYear(movie.release_date) ?: "",
                        style = textStyleRegular,
                        color = lightGreyColor,
                    )
                    HorizontalDetailsGenres(popularMoviesViewModel.getMovieGenres(movie.genre_ids))
                    Spacer(modifier = Modifier.height(15.dp))
                    Row {
                        Text(
                            modifier = Modifier.padding(end = 7.dp),
                            text = "${popularMoviesViewModel.calculatePercent(movie.vote_average)}%",
                            style = textStyleBold,
                            fontSize = 20.sp,
                        )
                        Text(
                            text = "user score",
                            style = textStyleRegular,
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
            ClickableContainer(navController)
            Column {
                TextInterBold("Overview", modifier = Modifier.padding(top = 33.dp, bottom = 14.dp))
                TextInterRegular(movie.overview)
            }
        }
    }
}

@Composable
fun ClickableContainer(navController: NavHostController) {

    var topButtonColor by remember {mutableStateOf(R.color.background_top_rate)}
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
                    buttonTopText = if (buttonTopText == "Rate it myself >") "You’ve rated this 0" else "Rate it myself >"
                    buttonBottomText = if (buttonBottomText == "add personal rating") "click to reset" else "add personal rating"
                }
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                        .background(color = colorResource(id = topButtonColor) )
                ) {
                    Text(
                        modifier = Modifier
                            .padding(6.dp)
                            .align(Alignment.Center),
                        text = buttonTopText,
                        color =  Color.White
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
                    Text(
                        modifier = Modifier
                            .padding(6.dp)
                            .align(Alignment.Center),
                        text = buttonBottomText,
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
                Text(
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
    val fontFamily = FontFamily(Font(R.font.inter_regular))
    val textStyle = TextStyle(
        fontFamily = fontFamily,
        fontSize = 12.sp,
        color = colorResource(id = R.color.grey_200),
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom
    ) {
        items.take(2).forEach { text ->
            Text(
                text = text.name,
                style = textStyle,
                maxLines = 1,
                modifier = Modifier
                    .padding(end = 12.dp)
                    .padding(horizontal = 0.dp, vertical = 3.dp)
            )
        }
    }
}

@Composable
fun Header(context: Context, movie: Movies, navController: NavHostController) {
    val imageLoader =
        remember { ImageLoader(context) }
    val request = ImageRequest.Builder(context)
        .data("https://media.themoviedb.org/t/p/w1920_and_h800_multi_faces" + movie.backdrop_path)
        .crossfade(true)
        .build()
    val bitmap: Bitmap = BitmapFactory.decodeResource(
        context.resources,
        R.drawable.ic_rate_star
    )
    Box(
    ) {
        SubcomposeAsyncImage(
            model = request,
            contentDescription = movie.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp),
            contentScale = ContentScale.FillHeight,
            imageLoader = imageLoader,
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
            RateStar(onClick = { /* Your click action */ }, bitmap)
        }
    }
}

@Composable
fun RateStar(onClick: () -> Unit, bitmap: Bitmap) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.padding(start = 160.dp),
    ) {
        val imageBitmap = bitmap.asImageBitmap()
        val icon: Painter = BitmapPainter(imageBitmap)
        Icon(
            painter = icon,
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
}

@Composable
fun TextTitle(movieTitle: String) {
    val fontFamily = FontFamily(Font(R.font.jomhuria_regular))
    val textStyle = TextStyle(
        fontFamily = fontFamily,
        fontSize = 96.sp,
        color = colorResource(id = R.color.white),
    )
    Text(
        modifier = Modifier
            .padding(horizontal = 32.dp)
            .padding(top = 20.dp),
        maxLines = 1,
        text = movieTitle,
        style = textStyle
    )
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
                text = "Back to Search",
                style = textStyle
            )
        }
    }
}
