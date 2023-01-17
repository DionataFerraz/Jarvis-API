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
    /* val teste = "SELECT\n" +
             "        *\n" +
             "FROM\n" +
             "        comic_book cb\n" +
             "LEFT JOIN\n" +
             "                (\n" +
             "                        SELECT\n" +
             "                                cbl.name, cbl.description, cbl.alternative_name, cbl.language, cbl.id as id_cbl, cbl.id_comic_book\n" +
             "                        FROM\n" +
             "                                comic_book_locale cbl\n" +
             "                        LEFT JOIN\n" +
             "                                 volume vol\n" +
             "                        ON\n" +
             "                                 cbl.id = vol.id_comic_book_locale\n" +
             "                )\n" +
             "        cbl\n" +
             "ON\n" +
             "        cb.id = cbl.id_comic_book\n" +
             "WHERE\n" +
             "        cb.id = :id\n" +
             "AND\n" +
             "       cbl.language = :language"*/

}

/*
SELECT
        *
FROM
        comic_book cb
LEFT JOIN
                (
                        SELECT
                                cbl.name, cbl.description, cbl.alternative_name, cbl.language, cbl.id as id_cbl, cbl.id_comic_book
                        FROM
                                comic_book_locale cbl
                        LEFT JOIN
                                 volume vol
                        ON
                                 cbl.id = vol.id_comic_book_locale
                )
        cbl
ON
        cb.id = cbl.id_comic_book
WHERE
        cb.id = 1
AND
        cbl.language = 'pt-BR'
* */