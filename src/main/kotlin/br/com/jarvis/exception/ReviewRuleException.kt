package br.com.jarvis.exception

object ReviewDuplicatedException : RuntimeException("Duplicate action")
object ReviewUpdateException : RuntimeException("Error to update review")
