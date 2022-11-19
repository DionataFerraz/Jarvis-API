package br.com.jarvis.domain.repository

import br.com.jarvis.domain.entity.AnimationEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AnimationRepository : JpaRepository<AnimationEntity, Long>
