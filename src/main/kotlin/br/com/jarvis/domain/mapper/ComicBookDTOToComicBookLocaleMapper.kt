package br.com.jarvis.domain.mapper

import br.com.jarvis.domain.entity.ComicBookEntity
import br.com.jarvis.domain.entity.ComicBookLocale
import br.com.jarvis.rest.controller.dto.ComicBookDTO

interface ComicBookDTOToComicBookLocaleMapper {
    fun mapFrom(dto: ComicBookDTO, comicBook: ComicBookEntity): ComicBookLocale
}