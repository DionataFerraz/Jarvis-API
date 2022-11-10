package br.com.jarvis.domain.repository

import br.com.jarvis.domain.entity.ComicBook
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ComicBookRepository : JpaRepository<ComicBook, Long> {

    @Query(
        " SELECT * FROM comic_book cb LEFT JOIN comic_book_locale cbl ON cb.id = cbl.id_comic_book WHERE cbl.language = :language",
        nativeQuery = true
    )
    fun findByLanguageComicBookLocaleIn(language: String?): List<ComicBook>

}