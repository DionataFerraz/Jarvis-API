package br.com.jarvis.domain.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDate
import java.util.Date
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

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
    var comicBook: ComicBook? = null

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_volume", referencedColumnName = "id")
    var volume: Volume? = null

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_chapter", referencedColumnName = "id")
    var chapter: ChapterEntity? = null

    @Column(name = "image_path", length = 500, nullable = false)
    @NotEmpty(message = "Image imagePath field is required.")
    lateinit var imagePath: String

    @Column(name = "description", length = 100, nullable = false)
    @NotEmpty(message = "Image description field is required.")
    lateinit var description: String

    constructor() {}

    constructor(
        imagePath: String,
        description: String,
        comicBook: ComicBook? = null,
        chapter: ChapterEntity? = null,
        volume: Volume? = null,
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
