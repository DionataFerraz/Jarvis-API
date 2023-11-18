package br.com.jarvis.rest.controller.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDate

data class ComicBookResponseDTO(
    val id: Long?,
    val comicType: String,
    val hasAnimation: Boolean,
    val name: String,
    val description: String,
    val userRating: Double = 0.0,
    val isNew: Boolean = false,
    val isFavorite: Boolean = false,
    val releaseDate: LocalDate,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val completionDate: LocalDate? = null,
    val imagePath: String,
    val coversImage: List<ImageResponseDTO>,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val authors: List<AuthorResponseDTO>? = null,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val animations: List<AnimationResponseDTO>? = null,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val volumes: List<VolumeResponseDTO>? = null,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val tags: List<TagsResponseDTO>? = null,
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