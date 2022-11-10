package br.com.jarvis.domain.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.validation.constraints.NotEmpty

@Entity
@Table(name = "comic_book_locale")
class ComicBookLocale {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @JsonIgnore
    @OneToMany(
        mappedBy = "locale",
        fetch = FetchType.LAZY,
        orphanRemoval = false,
        targetEntity = Volume::class,
        cascade = [CascadeType.ALL]
    )
    var volumes: Set<Volume> = emptySet()

    @JsonIgnore
    @OneToMany(
        mappedBy = "locale",
        fetch = FetchType.LAZY,
        orphanRemoval = false,
        targetEntity = ChapterEntity::class,
        cascade = [CascadeType.ALL]
    )
    var chapters: Set<ChapterEntity> = emptySet()

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_comic_book", referencedColumnName = "id")
    var comicBook: ComicBook? = null

    @Column(name = "name", length = 50, nullable = false)
    @NotEmpty(message = "Comic book locale name field is required.")
    lateinit var name: String

    @Column(name = "description", length = 100, nullable = false)
    @NotEmpty(message = "Comic book locale description field is required.")
    lateinit var description: String

    @Column(name = "alternative_name", length = 50)
    var alternativeName: String? = null

    @Column(name = "language", nullable = false)
    @NotEmpty(message = "Comic book language field is required.")
    lateinit var language: String

    constructor() {}

    constructor(
        name: String,
        comicBook: ComicBook,
        description: String,
        language: String,
        alternativeName: String? = null
    ) {
        this.name = name
        this.description = description
        this.comicBook = comicBook
        this.language = language
        this.alternativeName = alternativeName
    }

    override fun toString(): String {
        return "ComicBookLocale(" +
                "   comicBook = $comicBook," +
                "   name = $name," +
                "   description = $description," +
                "   locale = ${language}," +
                "   volumes = $volumes," +
                ")"
    }

}
