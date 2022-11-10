package br.com.jarvis.domain.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Entity
@Table(name = "volume")
class Volume {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    var id: Long? = null

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_comic_book_locale", referencedColumnName = "id")
    var locales: ComicBookLocale? = null

    @Column(name = "release_year", nullable = false)
    @NotEmpty(message = "Volume release year field is required.")
    lateinit var releaseYear: String

    @Column(name = "volume_number", nullable = false)
    @NotNull(message = "Volume number field is required.")
    var number: Int = 0

    @Column(name = "description", length = 1000, nullable = false)
    @NotEmpty(message = "Volume description field is required.")
    lateinit var description: String

    @Column(name = "isbn", length = 100, unique = true, nullable = false)
    @NotNull(message = "Volume isbn field is required.")
    var isbn: Long = 0

    @Column(name = "pages", length = 100, nullable = false)
    @NotNull(message = "Volume pages field is required.")
    var pages: Int = 0

    @Column(name = "book_cover_type", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Comic type field is required.")
    lateinit var bookCoverType: BookCoverType


    constructor() {}

    constructor(
        releaseYear: String,
        number: Int,
        description: String,
        isbn: Long,
        pages: Int,
        bookCoverType: BookCoverType,
        locales: ComicBookLocale? = null,
    ) {
        this.releaseYear = releaseYear
        this.number = number
        this.description = description
        this.isbn = isbn
        this.pages = pages
        this.bookCoverType = bookCoverType
        this.locales = locales
    }

    override fun toString(): String {
        return "Volume(" +
                "   releaseYear = $releaseYear," +
                "   number = $number," +
                "   isbn = $isbn," +
                "   locales = ${locales}," +
                "   isbn = ${isbn}," +
                "   pages = ${pages}," +
                "   bookCoverType = ${bookCoverType}," +
                ")"
    }
}
