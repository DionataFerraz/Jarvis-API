package br.com.jarvis.domain.repository

import br.com.jarvis.domain.entity.ReviewComicBookEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ReviewComicBookRepository : JpaRepository<ReviewComicBookEntity, Long> {
    @Query(
        value = "SELECT " +
                "IF(count(1) > 0, 'TRUE', 'FALSE') " +
                "FROM review_comic_book " +
                "WHERE id_user = :userId " +
                "AND id_comic_book = :comicBookId",
        nativeQuery = true
    )
    fun isDuplicate(comicBookId: Long, userId: Long?): Boolean

    @Query(
        value = "SELECT " +
                " * " +
                "FROM review_comic_book " +
                "WHERE id_user = :userId " +
                "AND id_comic_book = :comicBookId",
        nativeQuery = true
    )
    fun findByComicBookIdAndUserId(comicBookId: Long, userId: Long?): ReviewComicBookEntity

    @Query(
        value = "SELECT " +
                "    COALESCE(AVG(review), 0) AS average_review " +
                "FROM " +
                "    JARVIS_DB.review_comic_book " +
                "WHERE " +
                "    id_comic_book = :comicBookId",
        nativeQuery = true
    )
    fun findAverageReview(comicBookId: Long?): Double

}
