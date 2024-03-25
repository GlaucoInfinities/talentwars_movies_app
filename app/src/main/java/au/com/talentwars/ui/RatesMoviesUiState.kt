package au.com.talentwars.ui

import au.com.talentwars.data.model.Favourites

sealed interface RatesMoviesUiState {
    /**
     * Empty state when the screen is first shown
     */
    data object Initial : RatesMoviesUiState

    /**
     * Still loading
     */
    data object Loading : RatesMoviesUiState

    /**
     * Favourites Movies has been loaded
     */
    data class Success(val favourites: List<Favourites>) : RatesMoviesUiState

    /**
     * There was an error
     */
    data class Error(val errorMessage: String) : RatesMoviesUiState
}