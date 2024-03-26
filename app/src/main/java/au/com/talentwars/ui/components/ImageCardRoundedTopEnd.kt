package au.com.talentwars.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

@Composable
fun ImageCardRoundedTopEnd(urlImage: String) {

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(topEnd = 30.dp))
            .width(128.dp)
            .height(170.dp)
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        CachedAsyncImage(
            url = "https://media.themoviedb.org/t/p/w300_and_h450_bestv2$urlImage",
            modifier = Modifier
                .width(120.dp)
                .height(162.dp)
                .clip(RoundedCornerShape(0.dp, 30.dp, 0.dp, 0.dp)),
            contentScale = ContentScale.FillBounds,
        )
    }
}