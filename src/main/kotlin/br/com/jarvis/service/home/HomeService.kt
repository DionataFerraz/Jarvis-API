package br.com.jarvis.service.home

import br.com.jarvis.rest.controller.dto.response.ComicBookResponseDTO

interface HomeService {
    fun fetchAllComics(language: String): List<ComicBookResponseDTO>
}