package br.com.jarvis.domain.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDate
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@Table(name = "comic_book")
class ComicBook {
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

    @OneToMany(
        mappedBy = "comicBook",
        fetch = FetchType.LAZY,
        orphanRemoval = false,
        targetEntity = AuthorEntity::class,
        cascade = [CascadeType.ALL]
    )
    var author:  Set<AuthorEntity> = emptySet()

    @Column(name = "comic_type", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Comic type field is required.")
    lateinit var comicType: ComicType

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
        hasAnimation: Boolean,
        locales: Set<ComicBookLocale> = emptySet(),
        releaseDate: LocalDate,
        completionDate: LocalDate? = null
    ) {
        this.comicType = comicType
        this.hasAnimation = hasAnimation
        this.locales = locales
        this.releaseDate = releaseDate
        this.completionDate = completionDate
    }

    override fun toString(): String {
        return "ComicBook(" +
                "   id = $id," +
                "   comicType = $comicType," +
                "   hasAnimation = $hasAnimation," +
                "   releaseDate = $releaseDate," +
                "   completionDate = $completionDate," +
                "   locales = $locales," +
                ")"
    }
}
