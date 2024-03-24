package au.com.talentwars.ui.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.com.talentwars.data.FavouritesRepository
import au.com.talentwars.data.model.Favourites
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
                },
                onError = { error ->
                    _uiState.value = FavouritesMoviesUiState.Error(error)
                }
            )
        }
    }
}