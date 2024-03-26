package au.com.talentwars.ui

import au.com.talentwars.data.model.Favourites

sealed interface FavouritesMoviesUiState {
    /**
     * Empty state when the screen is first shown
     */
    data object Initial : FavouritesMoviesUiState

    /**
     * Still loading
     */
    data object Loading : FavouritesMoviesUiState

    /**
     * Popular Movies has been loaded
     */
    data class Success(val movies: List<Favourites>) : FavouritesMoviesUiState

    /**
     * There was an error
     */
    data class Error(val errorMessage: String) : FavouritesMoviesUiState
}