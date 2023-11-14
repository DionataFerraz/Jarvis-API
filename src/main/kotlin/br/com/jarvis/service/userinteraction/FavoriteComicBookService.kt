package br.com.jarvis.service.userinteraction

interface FavoriteComicBookService {
    fun favorite(comicBookId: Long)
    fun unFavorite(comicBookId: Long)
    fun readAll(comicBookId: Long, isReadAll: Boolean, userId: Long)
    fun hasAllVolume(comicBookId: Long, isReadAll: Boolean, userId: Long)
}