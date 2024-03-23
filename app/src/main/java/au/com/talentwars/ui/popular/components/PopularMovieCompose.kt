package au.com.talentwars.ui.popular.components

import androidx.compose.foundation.background
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import au.com.talentwars.R
import au.com.talentwars.data.model.Genres
import au.com.talentwars.data.model.Movies
import au.com.talentwars.ui.components.CachedAsyncImage
import au.com.talentwars.ui.components.TextInterBold
import au.com.talentwars.ui.components.TextInterRegular
import au.com.talentwars.ui.popular.PopularMoviesViewModel

@Composable
fun PopularMovieCompose(
    movie: Movies,
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.white),
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
            CardDetails(movie)
        }
    }
}

@Composable
fun CardDetails(movie: Movies) {
    val popularMoviesViewModel: PopularMoviesViewModel = hiltViewModel()
    Row {
        CachedAsyncImage(
            url = "https://media.themoviedb.org/t/p/w300_and_h450_bestv2" + movie.poster_path,
            modifier = Modifier
                .fillMaxHeight()
                .width(85.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier.padding(27.dp, 31.dp, 0.dp, 0.dp)
        )
        {
            TextInterBold(text = movie.title, maxLines = 1)
            TextInterRegular(
                modifier = Modifier.padding(0.dp, 5.dp),
                text = popularMoviesViewModel.getMovieYear(movie.release_date),
                color = colorResource(id = R.color.grey_200),
                fontSize = 12.sp,
            )
            Row {
                TextInterBold(
                    text = "${popularMoviesViewModel.calculatePercent(movie.vote_average)}%",
                    modifier = Modifier.padding(end = 7.dp),
                    fontSize = 12.sp,
                )
                TextInterRegular(
                    text = "user score",
                    fontSize = 12.sp,
                )
            }
            HorizontalGenres(popularMoviesViewModel.getMovieGenres(movie.genre_ids))
        }
    }
}

@Composable
fun HorizontalGenres(items: List<Genres>) {
    Row(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalAlignment = Alignment.Bottom
    ) {
        items.take(2).forEach { text ->
            TextInterRegular(
                text = text.name,
                fontSize = 12.sp,
                maxLines = 1,
                color = colorResource(id = R.color.grey_200),
                modifier = Modifier
                    .padding(end = 12.dp)
                    .background(
                        color = colorResource(id = R.color.grey_100),
                        shape = RoundedCornerShape(30.dp)
                    )
                    .padding(horizontal = 6.dp, vertical = 3.dp)
            )
        }
    }
}
