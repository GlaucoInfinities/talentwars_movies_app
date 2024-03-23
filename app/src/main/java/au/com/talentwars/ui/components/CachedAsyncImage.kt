package au.com.talentwars.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest

@Composable
fun CachedAsyncImage(url: String, modifier: Modifier? = Modifier, contentScale: ContentScale? = null) {
    val context = LocalContext.current

    val imageLoader =
        remember { ImageLoader(context) }
    val request = ImageRequest.Builder(context)
        .data(url)
        .crossfade(true)
        .build()

    SubcomposeAsyncImage(
        model = request,
        contentDescription = "CacheAsyncImage",
        modifier = modifier ?: Modifier,
        contentScale = contentScale ?: ContentScale.None,
        imageLoader = imageLoader
    )
}