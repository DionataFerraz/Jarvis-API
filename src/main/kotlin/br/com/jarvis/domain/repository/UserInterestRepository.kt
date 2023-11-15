package br.com.jarvis.domain.repository

import br.com.jarvis.domain.entity.UserInterestEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserInterestRepository : JpaRepository<UserInterestEntity, Long> {

    @Query(
        value = "SELECT " +
                "IF(count(1) > 0, 'TRUE', 'FALSE') " +
                "FROM user_interest " +
                "WHERE id_user = :userId " +
                "AND id_tag IN (:tagsIds)",
        nativeQuery = true
    )
    fun isDuplicate(tagsIds: List<Long>, userId: Long?): Boolean

    @Query(
        value = "DELETE " +
                "FROM user_interest " +
                "WHERE id_user = :userId ",
        nativeQuery = true
    )
    fun deleteAllByUserId(userId: Long?)

}