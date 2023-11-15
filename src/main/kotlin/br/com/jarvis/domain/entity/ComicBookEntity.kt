package br.com.jarvis.domain.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.time.LocalDate

@Entity
@Table(name = "comic_book")
class ComicBookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    var id: Long? = null

    @OneToMany(
        mappedBy = "comicBook",
        fetch = FetchType.LAZY,
        orphanRemoval = false,
        targetEntity = ComicBookLocale::class,
        cascade = [CascadeType.ALL]
    )
    var locales: Set<ComicBookLocale> = emptySet()

    // TODO: Preciso ajustar isso pois um author pode ter varios volumes
    @OneToMany(
        mappedBy = "comicBook",
        fetch = FetchType.LAZY,
        orphanRemoval = false,
        targetEntity = AuthorEntity::class,
        cascade = [CascadeType.ALL]
    )
    var author: Set<AuthorEntity> = emptySet()

    @OneToMany(
        mappedBy = "comicBook",
        fetch = FetchType.LAZY,
        orphanRemoval = false,
        targetEntity = AnimationEntity::class,
        cascade = [CascadeType.ALL]
    )
    var animation: Set<AnimationEntity> = emptySet()

    @OneToMany(
        mappedBy = "comicBook",
        fetch = FetchType.LAZY,
        orphanRemoval = false,
        targetEntity = ImageEntity::class,
        cascade = [CascadeType.ALL]
    )
    var images: Set<ImageEntity> = emptySet()

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "comic_book_tag",
        joinColumns = [JoinColumn(name = "comic_book_id")],
        inverseJoinColumns = [JoinColumn(name = "tag_id")]
    )
    var tags: Set<TagEntity> = emptySet()

    @Column(name = "comic_type", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Comic type field is required.")
    lateinit var comicType: ComicType

    @Column(name = "image_path", nullable = false)
    @NotEmpty(message = "Comic book image path field is required.")
    lateinit var imagePath: String

    @Column(name = "has_animation", nullable = false)
    @NotNull(message = "Comic has animation field is required.")
    var hasAnimation: Boolean = false

    @Column(name = "release_date", nullable = false)
    @NotNull(message = "Comic book locale release date field is required.")
    lateinit var releaseDate: LocalDate

    @Column(name = "completion_date")
    @JsonIgnore
    var completionDate: LocalDate? = null

    constructor() {}

    constructor(
        comicType: ComicType,
        imagePath: String,
        hasAnimation: Boolean,
        locales: Set<ComicBookLocale> = emptySet(),
        releaseDate: LocalDate,
        completionDate: LocalDate? = null,
        tags: Set<TagEntity> = emptySet(),
    ) {
        this.comicType = comicType
        this.imagePath = imagePath
        this.hasAnimation = hasAnimation
        this.locales = locales
        this.releaseDate = releaseDate
        this.completionDate = completionDate
        this.tags = tags
    }

    override fun toString(): String {
        return "ComicBook(" +
                "   id = $id," +
                "   comicType = $comicType," +
                "   imagePath = $imagePath," +
                "   hasAnimation = $hasAnimation," +
                "   releaseDate = $releaseDate," +
                "   completionDate = $completionDate," +
                "   locales = $locales," +
                "   tags = $tags," +
                ")"
    }
}
