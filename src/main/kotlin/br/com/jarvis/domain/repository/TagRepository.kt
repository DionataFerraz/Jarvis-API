package br.com.jarvis.domain.repository

import br.com.jarvis.domain.entity.TagEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TagRepository : JpaRepository<TagEntity, Long> {

    @Query(
        value = "SELECT " +
                "IF(count(1) > 0, 'TRUE', 'FALSE') " +
                "FROM tag " +
                "WHERE name = :name ",
        nativeQuery = true
    )
    fun isDuplicate(name: String): Boolean

}