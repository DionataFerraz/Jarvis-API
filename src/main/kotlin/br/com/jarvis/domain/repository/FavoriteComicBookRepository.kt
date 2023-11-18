package br.com.jarvis.domain.repository

import br.com.jarvis.domain.entity.FavoriteComicBookEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface FavoriteComicBookRepository : JpaRepository<FavoriteComicBookEntity, Long> {
    @Query(
        value = "SELECT " +
                "IF(count(1) > 0, 'TRUE', 'FALSE') " +
                "FROM favorite_comic_book " +
                "WHERE id_user = :userId " +
                "AND id_comic_book = :comicBookId",
        nativeQuery = true
    )
    fun isFavorite(comicBookId: Long?, userId: Long?): Boolean

    @Query(
        value = "SELECT " +
                " * " +
                "FROM favorite_comic_book " +
                "WHERE id_user = :userId " +
                "AND id_comic_book = :comicBookId",
        nativeQuery = true
    )
    fun findByComicBookIdAndUserId(comicBookId: Long, userId: Long?): FavoriteComicBookEntity

}
