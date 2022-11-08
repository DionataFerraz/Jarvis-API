package br.com.jarvis.domain.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.GenericGenerator
import java.util.UUID
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

    @Column(name = "comic_type", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Comic type field is required.")
    lateinit var comicType: ComicType

    @Column(name = "has_animation", nullable = false)
    @NotNull(message = "Comic has animation field is required.")
    var hasAnimation: Boolean = false

    @OneToMany(mappedBy = "comicBook", fetch = FetchType.LAZY, orphanRemoval = false, targetEntity = ComicBookLocale::class)
    var locales: Set<ComicBookLocale> = emptySet()

    constructor() {}

    constructor(comicType: ComicType, hasAnimation: Boolean, comicBooks: Set<ComicBookLocale> = emptySet()) {
        this.comicType = comicType
        this.hasAnimation = hasAnimation
        this.locales = comicBooks
    }

    override fun toString(): String {
        return "ComicBook(" +
                "   id = $id," +
                "   comicType = $comicType," +
                "   hasAnimation = $hasAnimation," +
                "   locales = $locales," +
                ")"
    }
}
