package br.com.jarvis.domain.repository

import br.com.jarvis.domain.entity.VolumeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VolumeRepository : JpaRepository<VolumeEntity, Long>

