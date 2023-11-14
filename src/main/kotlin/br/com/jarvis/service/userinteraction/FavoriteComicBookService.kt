package br.com.jarvis.service.userinteraction

interface FavoriteComicBookService {
    fun favorite(comicBookId: Long)
    fun unFavorite(comicBookId: Long)
}