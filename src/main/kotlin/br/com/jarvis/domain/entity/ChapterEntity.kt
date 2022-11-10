package br.com.jarvis.domain.entity

import com.fasterxml.jackson.annotation.JsonIgnore
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
@Table(name = "chapter")
class ChapterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    var id: Long? = null

    @JsonIgnore
    @OneToMany(
        mappedBy = "chapter",
        fetch = FetchType.LAZY,
        orphanRemoval = false,
        targetEntity = ImageEntity::class,
        cascade = [CascadeType.ALL]
    )
    var images: Set<ImageEntity> = emptySet()

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_comic_book_locale", referencedColumnName = "id")
    var locale: ComicBookLocale? = null

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_volume", referencedColumnName = "id")
    var volume: Volume? = null

    @Column(name = "release_year", nullable = false)
    @NotEmpty(message = "Chapter release year field is required.")
    lateinit var releaseYear: String

    @Column(name = "chapter_number", nullable = false)
    @NotNull(message = "Chapter number field is required.")
    var number: Int = 0

    @Column(name = "title", length = 100, nullable = false)
    @NotEmpty(message = "Chapter title field is required.")
    lateinit var title: String

    @Column(name = "isbn", unique = true)
    var isbn: Long? = null

    @Column(name = "pages")
    var pages: Int? = null

    @Column(name = "book_cover_type")
    @Enumerated(EnumType.STRING)
    var bookCoverType: BookCoverType? = null

    constructor() {}

    constructor(
        releaseYear: String,
        number: Int,
        title: String,
        isbn: Long? = null,
        pages: Int? = null,
        bookCoverType: BookCoverType? = null,
        locale: ComicBookLocale? = null,
        volume: Volume? = null,
    ) {
        this.releaseYear = releaseYear
        this.number = number
        this.title = title
        this.isbn = isbn
        this.pages = pages
        this.bookCoverType = bookCoverType
        this.locale = locale
        this.volume = volume
    }

    override fun toString(): String {
        return "ChapterEntity(" +
                "   releaseYear = $releaseYear," +
                "   number = $number," +
                "   isbn = $isbn," +
                "   locale = ${locale}," +
                "   isbn = ${isbn}," +
                "   pages = ${pages}," +
                "   bookCoverType = ${bookCoverType}," +
                ")"
    }
}
