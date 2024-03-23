package au.com.talentwars.ui.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import au.com.talentwars.R

@Composable
fun RateStar(onClick: () -> Unit) {
    val context = LocalContext.current

    val bitmap: Bitmap = BitmapFactory.decodeResource(
        context.resources,
        R.drawable.ic_rate_star
    )
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