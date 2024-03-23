package au.com.talentwars.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import au.com.talentwars.R

@Composable
fun SeekBar(
    progress: Float
) {
    Spacer(modifier = Modifier.height(3.dp))
    LinearProgressIndicator(
        progress = progress / 100,
        color = colorResource(id = R.color.background_track_color_slider),
        backgroundColor = colorResource(id = R.color.background_slider),
        modifier = Modifier
            .width(120.dp)
    )
}