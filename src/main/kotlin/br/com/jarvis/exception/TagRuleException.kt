package br.com.jarvis.exception

object TagDuplicatedException : RuntimeException("Duplicate action to save tag")
object TagUserInterestDuplicatedException : RuntimeException("Duplicate action to save interests")
object TagNotFoundException : RuntimeException(
    "You cannot delete tag because it not exist"
)
data class TagErrorException(override val message: String) : RuntimeException(
    "Error to remove tag: $message"
)
