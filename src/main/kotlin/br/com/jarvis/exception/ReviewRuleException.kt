package br.com.jarvis.exception

object ReviewDuplicatedException : RuntimeException("Duplicate action")
data class ReviewUpdateException(override val message: String) : RuntimeException("Error to update review: $message")
