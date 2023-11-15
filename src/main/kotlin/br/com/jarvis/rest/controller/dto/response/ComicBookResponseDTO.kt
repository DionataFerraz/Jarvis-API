package br.com.jarvis.rest.controller.dto.response

import java.time.LocalDate

data class ComicBookResponseDTO(
    val id: Long?,
    val comicType: String,
    val hasAnimation: Boolean,
    val name: String,
    val description: String,
    val language: String,
    val releaseDate: LocalDate,
    val completionDate: LocalDate? = null,
    val imagePath: String,
    val imageType: String? = null,
    val authors: List<AuthorResponseDTO>? = null,
    val animations: List<AnimationResponseDTO>? = listOf(),
    val volumes: List<VolumeResponseDTO>? = listOf(),
    val tags: List<TagsResponseDTO> = listOf(),
)

data class TagsResponseDTO(
    val id: Long?,
    val name: String,
)

data class ImageResponseDTO(
    val image: String,
    val description: String? = null,
)

data class VolumeResponseDTO(
    val id: Long?,
    val number: Int,
    val releaseYear: String,
    val description: String,
    val isbn: Long,
    val pages: Int,
    val bookCoverType: String,
    val images: List<ImageResponseDTO>? = null,
)