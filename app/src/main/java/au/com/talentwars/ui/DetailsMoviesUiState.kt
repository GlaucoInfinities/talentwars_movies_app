package au.com.talentwars.ui

import au.com.talentwars.data.model.Details
import au.com.talentwars.data.model.Movies

sealed interface DetailsMoviesUiState {
    /**
     * Empty state when the screen is first shown
     */
    data object Initial : DetailsMoviesUiState

    /**
     * Still loading
     */
    data object Loading : DetailsMoviesUiState

    /**
     * Articles has been loaded
     */
    data class Success(val movies: Details) : DetailsMoviesUiState

    /**
     * There was an error
     */
    data class Error(val errorMessage: String) : DetailsMoviesUiState
}