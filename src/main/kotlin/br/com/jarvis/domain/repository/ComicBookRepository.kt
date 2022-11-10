package br.com.jarvis.domain.repository

import br.com.jarvis.domain.entity.ComicBook
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface ComicBookRepository : JpaRepository<ComicBook, Long> {

    @Query(
        "SELECT * FROM comic_book cb LEFT JOIN comic_book_locale cbl ON cb.id = cbl.id_comic_book WHERE cbl.language = :language",
        nativeQuery = true
    )
    fun findByLanguageComicBookLocaleIn(language: String?): List<ComicBook>

    @Query(
        "SELECT" +
                "   * " +
                "FROM " +
                "   comic_book cb " +
                "LEFT JOIN " +
                "   (" +
                "       SELECT" +
                "           cbl.name, cbl.description, cbl.alternative_name, cbl.language, cbl.id as id_cbl, cbl.id_comic_book" +
                "       FROM" +
                "           comic_book_locale cbl" +
                "       LEFT JOIN" +
                "           volume vol" +
                "       ON" +
                "           cbl.id = vol.id_comic_book_locale" +
                "   )" +
                "   cbl " +
                "ON" +
                "   cb.id = cbl.id_comic_book " +
                "WHERE" +
                "   cb.id = :id " +
                "AND" +
                "   cbl.language = :language",
        nativeQuery = true
    )
    fun findByIdComicBookLocaleIn(id: Long, language: String?): Optional<ComicBook>

}