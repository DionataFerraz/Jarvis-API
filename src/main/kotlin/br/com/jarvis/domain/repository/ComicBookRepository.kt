package br.com.jarvis.domain.repository

import br.com.jarvis.domain.entity.ComicBookEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ComicBookRepository : JpaRepository<ComicBookEntity, Long> {

    @Query(
        "SELECT cb.*, cbl.alternative_name, cbl.description, cbl.`language`, cbl.name " +
                "FROM comic_book cb " +
                "LEFT JOIN comic_book_locale cbl ON cb.id = cbl.id_comic_book " +
                "WHERE cbl.language = :language ",
        nativeQuery = true
    )
    fun findByLanguageComicBookLocaleIn(language: String?): List<ComicBookEntity>

    @Query(
        "SELECT " +
                "    cb.*, " +
                "    cbl.alternative_name, " +
                "    cbl.description, " +
                "    cbl.`language`, " +
                "    cbl.name, " +
                "    COALESCE(reviews.average_review, 0) AS average_review, " +
                "    COALESCE(reviews.review_count, 0) AS review_count " +
                "FROM " +
                "    comic_book cb " +
                "LEFT JOIN " +
                "    ( " +
                "        SELECT " +
                "            id_comic_book, " +
                "            AVG(review) AS average_review, " +
                "            COUNT(review) AS review_count " +
                "        FROM " +
                "            JARVIS_DB.review_comic_book " +
                "        GROUP BY " +
                "            id_comic_book " +
                "        ORDER BY " +
                "            review_count DESC, average_review DESC " +
                "    ) AS reviews ON cb.id = reviews.id_comic_book " +
                "LEFT JOIN " +
                "    comic_book_locale cbl ON cb.id = cbl.id_comic_book AND cbl.language = :language " +
                "ORDER BY " +
                "    COALESCE(reviews.review_count, 0) DESC, " +
                "    (COALESCE(reviews.average_review, 0) * 2) DESC; ",
        nativeQuery = true
    )
    fun findComicBookByLanguage(language: String?): List<ComicBookEntity>

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
    fun findByIdComicBookLocaleIn(id: Long, language: String?): Optional<ComicBookEntity>

}