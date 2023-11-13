package br.com.jarvis.domain.repository

import br.com.jarvis.domain.entity.UserComicBookInteractionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UseComicBookInteractionRepository : JpaRepository<UserComicBookInteractionEntity, Long> {
    @Query(
        value = "SELECT " +
                "IF(count(1) > 0, 'TRUE', 'FALSE') " +
                "FROM user_volume_interaction " +
                "WHERE id_user = :userId " +
                "AND id_comic_book = :comicBookId",
        nativeQuery = true
    )
    fun isDuplicate(comicBookId: Long, userId: Long?): Boolean

    @Query(
        value = "SELECT " +
                " * " +
                "FROM user_volume_interaction " +
                "WHERE id_user = :userId " +
                "AND id_comic_book = :comicBookId",
        nativeQuery = true
    )
    fun findByComicBookIdAndUserId(comicBookId: Long, userId: Long?): UserComicBookInteractionEntity

}
