package br.com.jarvis.service.userinteraction

interface ReviewComicBookService {
    fun review(comicBookId: Long, review: Double)
    fun updateReview(comicBookId: Long, review: Double)
}