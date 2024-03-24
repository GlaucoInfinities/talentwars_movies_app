package au.com.talentwars.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

@Composable
fun ImageCardRoundedTopEnd(urlImage: String, modifier: Modifier? = Modifier) {
    CachedAsyncImage(
        url = "https://media.themoviedb.org/t/p/w300_and_h450_bestv2$urlImage",
        modifier = Modifier
            .clip(RoundedCornerShape(0.dp, 40.dp, 0.dp, 0.dp))
            .then(modifier ?: Modifier),
        contentScale = ContentScale.FillBounds,
    )
}