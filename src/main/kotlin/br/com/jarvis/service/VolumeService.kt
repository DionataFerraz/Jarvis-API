package br.com.jarvis.service

import br.com.jarvis.rest.controller.dto.ComicBookDTO

interface VolumeService {
    fun fetchAllComics(id: Long, language: String?): ComicBookDTO
}