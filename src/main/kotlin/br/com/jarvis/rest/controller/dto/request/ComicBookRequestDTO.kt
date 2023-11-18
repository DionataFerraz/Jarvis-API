package br.com.jarvis.rest.controller.dto.request

import java.time.LocalDate

data class ComicBookRequestDTO(
    val id: Long?,
    val comicType: String,
    val hasAnimation: Boolean,
    val name: String,
    val description: String,
    val language: String,
    val releaseDate: LocalDate,
    val completionDate: LocalDate? = null,
    val imagePath: String,
    val coversImage: List<ImageRequestDTO>,
    val imageType: String? = null,
    val authors: List<AuthorRequestDTO>? = null,
    val animations: List<AnimationRequestDTO>? = listOf(),
    val volumes: List<VolumeRequestDTO>? = listOf(),
    val tags: List<Long> = listOf(),
)

data class ImageRequestDTO(
    val image: String,
    val description: String? = null,
)

data class VolumeRequestDTO(
    val id: Long?,
    val number: Int,
    val releaseYear: String,
    val description: String,
    val isbn: Long,
    val pages: Int,
    val bookCoverType: String,
    val images: List<ImageRequestDTO>? = null,
)