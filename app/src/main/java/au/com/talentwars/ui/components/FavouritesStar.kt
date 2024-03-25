package au.com.talentwars.ui.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import au.com.talentwars.data.model.Movies
import au.com.talentwars.ui.favourites.FavouritesViewModel

@Composable
fun FavouritesStar(
    onClick: () -> Unit,
    modifier: Modifier? = Modifier,
    rated: Boolean? = false,
    movie: Movies
) {

    val viewModel: FavouritesViewModel = hiltViewModel()
    val context = LocalContext.current

    val resourceId = viewModel.getImageResource()
    val bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, resourceId)

    //val favouritesState by viewModel.favouritesState.observeAsState()
    LaunchedEffect(viewModel) {
        viewModel.checkFavourites(movie)
    }



    IconButton(
        onClick = { viewModel.onRateStarClicked(movie) },
        modifier = modifier ?: Modifier,
    ) {
        val imageBitmap = bitmap.asImageBitmap()
        val icon: Painter = BitmapPainter(imageBitmap)
        Icon(
            painter = icon,
            contentDescription = null,
            tint = Color.Unspecified,
        )
    }
}