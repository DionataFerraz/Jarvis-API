package br.com.jarvis.service

import br.com.jarvis.rest.controller.dto.ComicBookDTO

interface ComicBookService {
    fun save(dto: ComicBookDTO)
    fun fetchAllComics(language: String): List<ComicBookDTO>
}