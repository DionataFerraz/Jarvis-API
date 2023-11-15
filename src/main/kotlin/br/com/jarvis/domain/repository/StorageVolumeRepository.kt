package br.com.jarvis.domain.repository

import br.com.jarvis.domain.entity.StorageVolumeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface StorageVolumeRepository : JpaRepository<StorageVolumeEntity, Long> {
    @Query(
        value = "SELECT " +
                "IF(count(1) > 0, 'TRUE', 'FALSE') " +
                "FROM storage_volume " +
                "WHERE id_user = :userId " +
                "AND id_volume = :volumeId",
        nativeQuery = true
    )
    fun isDuplicate(volumeId: Long, userId: Long?): Boolean

    @Query(
        value = "SELECT " +
                " * " +
                "FROM storage_volume " +
                "WHERE id_user = :userId " +
                "AND id_volume = :volumeId",
        nativeQuery = true
    )
    fun findByVolumeIdAndUserId(volumeId: Long, userId: Long?): StorageVolumeEntity

}
