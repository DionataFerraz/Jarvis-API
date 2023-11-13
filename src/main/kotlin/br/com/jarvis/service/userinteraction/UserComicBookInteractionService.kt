package br.com.jarvis.service.userinteraction

interface UserComicBookInteractionService {
    fun favorite(comicBookId: Long)
    fun unFavorite(comicBookId: Long)
    fun review(comicBookId: Long, review: Double, userId: Long)
    fun readAll(comicBookId: Long, isReadAll: Boolean, userId: Long)
    fun hasAllVolume(comicBookId: Long, isReadAll: Boolean, userId: Long)
}