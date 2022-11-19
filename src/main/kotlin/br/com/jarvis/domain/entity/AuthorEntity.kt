package br.com.jarvis.domain.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Entity
@Table(name = "author")
class AuthorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    var id: Long? = null

    // TODO: Preciso ajustar isso pois um author pode ter varios volumes
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_comic_book", referencedColumnName = "id")
    var comicBook: ComicBook? = null

    @Column(name = "name", length = 500, nullable = false)
    @NotEmpty(message = "Author name field is required.")
    lateinit var name: String

    @Column(name = "birthday", nullable = false)
    @NotNull(message = "Author birthday field is required.")
    lateinit var birthday: Date

    @Column(name = "synopsis", length = 500, nullable = false)
    @NotEmpty(message = "Author synopsis field is required.")
    lateinit var synopsis: String

    @Column(name = "image_path", length = 500, nullable = false)
    @NotEmpty(message = "Author imagePath field is required.")
    lateinit var imagePath: String

    constructor() {}

    constructor(
        comicBook: ComicBook? = null,
        name: String,
        birthday: Date,
        synopsis: String,
        imagePath: String
    ) {
        this.name = name
        this.comicBook = comicBook
        this.birthday = birthday
        this.synopsis = synopsis
        this.imagePath = imagePath
    }

    override fun toString(): String {
        return "AuthorEntity(" +
                "   id = $id," +
                "   name = $name," +
                "   birthday = $birthday," +
                "   synopsis = $synopsis," +
                "   imagePath = $imagePath," +
                ")"
    }
}
