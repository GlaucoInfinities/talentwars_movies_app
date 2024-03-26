package au.com.talentwars.ui.rated

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import au.com.talentwars.R
import au.com.talentwars.data.model.Movies
import au.com.talentwars.ui.components.ButtonBack
import au.com.talentwars.ui.components.CachedAsyncImage
import au.com.talentwars.ui.components.FavouritesStar
import au.com.talentwars.ui.components.ImageCardRoundedTopEnd
import au.com.talentwars.ui.components.RateClickable
import au.com.talentwars.ui.components.Screen
import au.com.talentwars.ui.components.TextInterRegular
import au.com.talentwars.ui.components.TextJomhuriaRegular


@Composable
fun RatedScreen(
    navController: NavHostController,
    movie: Movies,
    rating: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.green_background))
    ) {

        Header(navController, movie = movie)
        CenterContainer(navController, movie = movie,rating)
    }
}

@Composable
fun Header(navController: NavHostController, movie: Movies) {
    Box {
        CachedAsyncImage(
            url = "https://media.themoviedb.org/t/p/w1920_and_h800_multi_faces" + movie.backdrop_path,
            modifier = Modifier
                .fillMaxWidth()
                .height(448.dp),
            contentScale = ContentScale.FillHeight
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(448.dp)
                .background(Color.Black.copy(alpha = 0.8f))
        )
        Column {
            ButtonBack(navController)
            TextTitle(movieTitle = movie.title)
        }
    }
}

@Composable
fun CenterContainer(navController: NavHostController, movie: Movies, rating: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 50.dp)
            .padding(top = 128.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                modifier = Modifier
                    .offset(x = (-0).dp, y = (9).dp),
                verticalArrangement = Arrangement.Top
            ) {
                FavouritesStar(
                    modifier = Modifier.padding(start = 152.dp),
                    movie = movie
                )
            }

            ImageCardRoundedTopEnd(
                urlImage = movie.poster_path,
            )

            Column(
                modifier = Modifier
                    .padding(top = 27.dp),
            ) {
                RateClickable(rating)
            }

            GoToFavourites(navController)
        }
    }
}

@Composable
fun TextTitle(movieTitle: String) {
    Column(
        modifier = Modifier
            .padding(horizontal = 32.dp)
    ) {
        TextJomhuriaRegular(
            modifier = Modifier
                .padding(top = 15.dp),
            maxLines = 1,
            text = movieTitle,
            fontSize = 96.sp,
            color = colorResource(id = R.color.white)
        )
        Column(
            modifier = Modifier
                .offset(x = (-0).dp, y = (-25).dp),
            verticalArrangement = Arrangement.Top
        )
        {

            TextJomhuriaRegular(
                modifier = Modifier
                    .padding(top = 10.dp),
                maxLines = 1,
                text = "You rated this",
                fontSize = 30.sp,
                color = colorResource(id = R.color.white)
            )
        }

    }
}

@Composable
fun GoToFavourites(navController: NavHostController) {
    Column(
        modifier = Modifier
            .padding(top = 21.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                navController.navigate(
                    route = Screen.FavouritesScreen.route
                )
            },
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = colorResource(id = R.color.white),
            ),
            shape = RoundedCornerShape(80.dp),
            elevation = ButtonDefaults.elevation(defaultElevation = 8.dp)
        ) {
            TextInterRegular(
                modifier = Modifier.padding(horizontal = 50.dp, vertical = 10.dp),
                text = "Go to favourites",
                color = colorResource(id = R.color.black)
            )
        }
    }
}