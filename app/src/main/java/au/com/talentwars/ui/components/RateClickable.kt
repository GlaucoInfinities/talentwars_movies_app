package au.com.talentwars.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import au.com.talentwars.R

@Composable
fun RateClickable(rating: String) {

    Column(
        modifier = Modifier
            .width(170.dp)
            .clickable {
            }
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                    .background(color = colorResource(id = R.color.background_top_rate_pressed))
            ) {
                TextInterRegular(
                    modifier = Modifier
                        .padding(6.dp)
                        .align(Alignment.Center),
                    text = "You’ve rated this $rating",
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
                    text = "click to reset",
                    fontSize = 12.sp,
                    color = colorResource(id = R.color.text_bottom_rated)
                )
            }
        }
    }
}