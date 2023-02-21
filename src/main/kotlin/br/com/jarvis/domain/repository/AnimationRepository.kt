package br.com.jarvis.domain.repository

import br.com.jarvis.domain.entity.AnimationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AnimationRepository : JpaRepository<AnimationEntity, Long>
