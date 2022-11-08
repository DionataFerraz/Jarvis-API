package br.com.jarvis.domain.repository

import br.com.jarvis.domain.entity.ComicBook
import br.com.jarvis.domain.entity.ComicBookLocale
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface ComicBookRepository : JpaRepository<ComicBook, Long> {

    @Query(" SELECT * FROM comic_book cb LEFT JOIN comic_book_locale WHERE cb.id = comic_book_locale.id_comic_book", nativeQuery = true)
    fun findFetchComicBooks(): Optional<ComicBook>

}