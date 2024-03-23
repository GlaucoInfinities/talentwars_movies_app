package au.com.talentwars.ui.favourites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.com.talentwars.data.DetailsRepository
import au.com.talentwars.data.FavouritesRepository
import au.com.talentwars.data.model.Details
import au.com.talentwars.data.model.Favourites
import au.com.talentwars.data.model.Movies
import au.com.talentwars.ui.DetailsMoviesUiState
import au.com.talentwars.ui.FavouritesMoviesUiState
import au.com.talentwars.ui.PopularMoviesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val favouritesRepository: FavouritesRepository
) : ViewModel() {
    val favourites: Flow<List<Favourites>> = favouritesRepository.allFavouritesFromDataBase


    fun saveFavourites(movie: Movies) {
        viewModelScope.launch {
            favouritesRepository.saveDBFavourites(
                Favourites(
                    id = movie.id,
                    countRating = 1,
                    backdropPath = movie.poster_path
                )
            )
        }
    }
}