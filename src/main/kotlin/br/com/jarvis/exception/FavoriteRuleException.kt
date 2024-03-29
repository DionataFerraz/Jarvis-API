package br.com.jarvis.exception

object FavoriteDuplicatedException : RuntimeException("Duplicate action")
object FavoriteNotFoundException : RuntimeException(
    "You cannot delete the favorite comic book because it is not yet a favorite"
)

data class FavoriteErrorException(override val message: String) : RuntimeException("Error to remove favorite: $message")
