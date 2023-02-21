package br.com.jarvis.domain.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.validation.constraints.NotEmpty

@Entity
@Table(name = "image")
class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    var id: Long? = null

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_comic_book", referencedColumnName = "id")
    var comicBook: ComicBookEntity? = null

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_volume", referencedColumnName = "id")
    var volume: VolumeEntity? = null

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_chapter", referencedColumnName = "id")
    var chapter: ChapterEntity? = null

    @Column(name = "image_path", unique = true, length = 500, nullable = false)
    @NotEmpty(message = "Image imagePath field is required.")
    lateinit var imagePath: String

    @Column(name = "description", length = 100)
    var description: String? = null

    constructor() {}

    constructor(
        imagePath: String,
        description: String? = null,
        comicBook: ComicBookEntity? = null,
        chapter: ChapterEntity? = null,
        volume: VolumeEntity? = null,
    ) {
        this.imagePath = imagePath
        this.description = description
        this.comicBook = comicBook
        this.chapter = chapter
        this.volume = volume
    }

    override fun toString(): String {
        return "ImageEntity(" +
                "   imagePath = $imagePath," +
                "   description = $description," +
                ")"
    }
}
