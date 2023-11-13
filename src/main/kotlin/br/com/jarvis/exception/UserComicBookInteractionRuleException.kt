package br.com.jarvis.exception

object UserComicBookInteractionDuplicatedException : RuntimeException("Duplicate action")
object UserComicBookInteractionNotFoundException : RuntimeException(
    "You cannot delete the favorite comic book because it is not yet a favorite"
)
