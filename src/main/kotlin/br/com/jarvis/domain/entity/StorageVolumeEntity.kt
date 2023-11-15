package br.com.jarvis.domain.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "storage_volume")
class StorageVolumeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    var id: Long? = null,
    @Column(nullable = false)
    @NotNull(message = "IsRead field is required.")
    val hasVolume : Boolean = false,
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_volume", referencedColumnName = "id")
    var volumeEntity: VolumeEntity? = null,
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    var userEntity: UserEntity? = null
)
