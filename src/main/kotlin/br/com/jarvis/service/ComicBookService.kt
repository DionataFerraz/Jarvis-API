package br.com.jarvis.service

import br.com.jarvis.rest.controller.dto.request.ComicBookRequestDTO
import br.com.jarvis.rest.controller.dto.response.ComicBookResponseDTO

interface ComicBookService {
    fun save(dto: ComicBookRequestDTO)
    fun fetchAllComics(language: String): List<ComicBookResponseDTO>
    fun fetchComic(id: Long, language: String): ComicBookResponseDTO
}