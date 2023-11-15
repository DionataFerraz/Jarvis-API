package br.com.jarvis.service

import br.com.jarvis.rest.controller.dto.request.VolumeRequestDTO
import br.com.jarvis.rest.controller.dto.response.VolumeResponseDTO

interface VolumeService {
    fun saveById(id: Long, volumes: List<VolumeRequestDTO>? = listOf())
    fun fetchVolume(id: Long, language: String): VolumeResponseDTO
    fun fetchAllVolumes(id: Long, language: String): List<VolumeResponseDTO>
}