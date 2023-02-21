package br.com.jarvis.domain.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.validation.constraints.NotEmpty

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
        targetEntity = VolumeEntity::class,
        cascade = [CascadeType.ALL]
    )
    var volumes: Set<VolumeEntity> = emptySet()

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
    var comicBook: ComicBookEntity? = null

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
        comicBook: ComicBookEntity,
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
