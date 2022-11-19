package br.com.jarvis.domain.repository

import br.com.jarvis.domain.entity.AuthorEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AuthorRepository : JpaRepository<AuthorEntity, Long>

