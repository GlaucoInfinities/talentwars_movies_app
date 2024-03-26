package au.com.talentwars.ui.favourites

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.com.talentwars.R
import au.com.talentwars.data.FavouritesRepository
import au.com.talentwars.data.model.Favourites
import au.com.talentwars.data.model.Movies
import au.com.talentwars.data.model.toFavourites
import au.com.talentwars.ui.FavouritesMoviesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val favouritesRepository: FavouritesRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<FavouritesMoviesUiState> =
        MutableStateFlow(FavouritesMoviesUiState.Initial)
    val uiState: StateFlow<FavouritesMoviesUiState> = _uiState.asStateFlow()

    private val favouritesList = mutableListOf<Favourites>()
    private val favouritesState = mutableStateOf(false)

    private val imageResources = mutableStateListOf(
        R.drawable.ic_rate_star,
        R.drawable.ic_rated_star
    )

    init {
        _uiState.value = FavouritesMoviesUiState.Loading
        loadFavouritesMovies()
    }

    private fun loadFavouritesMovies() {
        viewModelScope.launch(Dispatchers.IO) {
             favouritesRepository.loadFavouritesFromServer(
                 onSuccess = { favourites ->
                     favouritesList.addAll(favourites)
                     _uiState.value = FavouritesMoviesUiState.Success(favouritesList.toList())
                     //updateListFavourites(favourites)
                 },
                 onError = { error ->
                     _uiState.value = FavouritesMoviesUiState.Error(error)
                 }
             )
        }
    }

    fun checkFavourites(movie: Movies) {
        viewModelScope.launch(Dispatchers.IO) {
            val favourites = favouritesRepository.getFavouritesByID(movie.id)
            if (favourites != null) {
                updateImage()
            }
        }
    }

    fun onRateStarClicked(movie: Movies) {
        updateRateServer(movie, !favouritesState.value)
    }

    private fun updateImage() {
        favouritesState.value = !favouritesState.value
    }

    private fun updateRateServer(movie: Movies, favourite: Boolean) {
        favouritesRepository.addFavouriteMovieFromServer(
            movie.id,
            favourite,
            onSuccess = { rates ->
                val favourites = movie.toFavourites()
                viewModelScope.launch {
                    if(!favouritesState.value){
                        favouritesRepository.saveFavourites(favourites)
                    }
                    else{
                        favouritesRepository.deleteFavourites(favourites)
                    }
                    updateImage()
                }
            },
            onError = { error ->
            }
        )
    }

    fun getImageResource(): Int {
        return imageResources[if (favouritesState.value) 1 else 0]
    }

   /* private fun updateListFavourites(favourites: List<Favourites>) {
        viewModelScope.launch(Dispatchers.IO) {
            favourites.forEach { favourite ->
                favouritesRepository.saveFavourites(favourite)
            }
        }
    }*/
}