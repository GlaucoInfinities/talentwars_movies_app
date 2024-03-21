package au.com.talentwars.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import au.com.talentwars.R
import au.com.talentwars.data.model.Movies
import au.com.talentwars.ui.theme.TalentwarsTheme
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest


@Composable
fun PopularMovieCompose(movie: Movies,calculatePercent: (Double) -> Int,getMovieYear: (String) -> String, onClick: () -> Unit) {
    //IMAGE CACHED
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

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 20.dp)
            .height(131.dp)
    ) {
        Column(modifier = Modifier
            .clickable {
                onClick()
            }) {
            Row {
                SubcomposeAsyncImage(
                    model = request,
                    contentDescription = movie.title,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(85.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.FillBounds,
                    imageLoader = imageLoader
                )
                Column(
                    modifier = Modifier.padding(27.dp, 31.dp, 0.dp, 0.dp)
                )
                {
                    Text(
                        text = movie.title ?: "",
                        style = textStyleBold,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Text(
                        modifier = Modifier.padding(0.dp, 5.dp),
                        text = getMovieYear(movie.release_date) ?: "",
                        style = textStyleRegular,
                        color = lightGreyColor,
                    )
                    Row {
                        Text(
                            modifier = Modifier.padding(end = 7.dp),
                            text = "${calculatePercent(movie.vote_average)}%",
                            style = textStyleBold,
                            fontSize = 12.sp,
                        )
                        Text(
                            text = "user score",
                            style = textStyleRegular,
                            fontSize = 12.sp,
                        )
                    }
                }
            }
        }
    }
}

@Preview("default")
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("large font", fontScale = 2f)
@Composable
fun PopularMoviesPreview() {
    TalentwarsTheme {
        PopularMovieCompose(
            Movies(
                1011985,
                false,
                "/mExN6lJHmLeGjwDmDrNNjR4MdCq.jpg",
                listOf(28, 12, 16, 35, 10751),
                "en",
                "Kung Fu Panda 4",
                "Po is gearing up to become the spiritual leader of his Valley of Peace, but also needs someone to take his place as Dragon Warrior. As such, he will train a new kung fu practitioner for the spot and will encounter a villain called the Chameleon who conjures villains from the past.",
                6623.028,
                "/wkfG7DaExmcVsGLR4kLouMwxeT5.jpg",
                "2024-03-02",
                "Kung Fu Panda 4",
                false,
                6.858,
                194
            ),
        ) {}
    }
}

fun PopularMovieCompose(movie: Movies, onClick: () -> Unit) {

}

