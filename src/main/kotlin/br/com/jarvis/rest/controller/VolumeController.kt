package br.com.jarvis.rest.controller

import br.com.jarvis.rest.controller.dto.request.VolumeRequestDTO
import br.com.jarvis.rest.controller.dto.response.VolumeResponseDTO
import br.com.jarvis.service.VolumeService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/volume")
class VolumeController(private val service: VolumeService) {

    @PostMapping("{id}")
    fun saveComicsById(
        @PathVariable id: Long,
        @RequestBody dto: @Valid List<VolumeRequestDTO>
    ) {
        service.saveById(id = id, volumes = dto)
    }

    @GetMapping("{id}")
    fun fetchVolume(
        @PathVariable id: Long,
        @RequestHeader("Accept-Language") language: String
    ): VolumeResponseDTO {
        return service.fetchVolume(id = id, language = language)
    }

    @GetMapping("{id}/all")
    fun fetchAllVolumesByLanguage(
        @PathVariable id: Long,
        @RequestHeader("Accept-Language") language: String
    ): List<VolumeResponseDTO> {
        return service.fetchAllVolumes(id = id, language = language)
    }

}



