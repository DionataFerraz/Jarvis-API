package br.com.jarvis.domain.repository

import br.com.jarvis.domain.entity.ComicBookEntity
import br.com.jarvis.domain.entity.ComicBookLocale
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ComicBookLocaleRepository : JpaRepository<ComicBookLocale, Long> {
    fun findByComicBookIn(comicBook: List<ComicBookEntity>): Set<ComicBookLocale>

    fun findByName(name: String?): Set<ComicBookLocale>
}
