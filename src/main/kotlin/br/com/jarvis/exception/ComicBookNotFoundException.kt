package br.com.jarvis.exception

object ComicBookNotFoundException : RuntimeException("ComicBook not found.")
class ComicBookException(override val message: String) : RuntimeException(message)

object ComicBookExistsException : RuntimeException("This ComicBook already exists!")

object ComicBookNeedsImageTypeException : RuntimeException("This ComicBook need imageType")
