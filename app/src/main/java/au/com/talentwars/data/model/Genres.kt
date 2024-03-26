package au.com.talentwars.data.model

data class RequestGenresMovies(
    val genres: List<Genres>,
)

data class Genres(
    val id: Int,
    val name: String
)