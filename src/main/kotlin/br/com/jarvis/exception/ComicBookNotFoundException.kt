package br.com.jarvis.exception

object ComicBookNotFoundException : RuntimeException("ComicBook not found.")

object ComicBookExistsException : RuntimeException("This ComicBook already exists!")
