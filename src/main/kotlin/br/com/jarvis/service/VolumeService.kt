package br.com.jarvis.service

import br.com.jarvis.rest.controller.dto.ComicBookDTO
import br.com.jarvis.rest.controller.dto.VolumeDTO

interface VolumeService {
    fun saveById(id: Long, volumes: List<VolumeDTO>? = listOf())
    fun fetchVolume(id: Long, language: String): VolumeDTO
    fun fetchAllVolumes(id: Long, language: String): List<VolumeDTO>
}