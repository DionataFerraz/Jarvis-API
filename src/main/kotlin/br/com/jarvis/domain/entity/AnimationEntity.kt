package br.com.jarvis.domain.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.util.Date

@Entity
@Table(name = "animation")
class AnimationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    var id: Long? = null

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_comic_book", referencedColumnName = "id")
    var comicBook: ComicBookEntity? = null

    @Column(name = "name", length = 500, nullable = false)
    @NotEmpty(message = "Animation name field is required.")
    lateinit var name: String

    @Column(name = "release_date", nullable = false)
    @NotNull(message = "Animation release date field is required.")
    lateinit var releaseDate: Date

    @Column(name = "completion_date", nullable = false)
    var completionDate: Date? = null

    @Column(name = "episode_qtd", nullable = false)
    @NotNull(message = "Animation episode qtd field is required.")
    var episodeQtd: Int = 0

    @Column(name = "season_qtd", nullable = false)
    @NotNull(message = "Animation season qtd field is required.")
    var seasonQtd: Int = 0

    @Column(name = "image_path", length = 500, nullable = false)
    @NotEmpty(message = "Animation imagePath field is required.")
    lateinit var imagePath: String

    constructor() {}

    constructor(
        comicBook: ComicBookEntity? = null,
        name: String,
        releaseDate: Date,
        completionDate: Date? = null,
        episodeQtd: Int,
        seasonQtd: Int,
        imagePath: String,
    ) {
        this.name = name
        this.comicBook = comicBook
        this.releaseDate = releaseDate
        this.completionDate = completionDate
        this.episodeQtd = episodeQtd
        this.seasonQtd = seasonQtd
        this.imagePath = imagePath
    }

    override fun toString(): String {
        return "AnimationEntity(" +
                "   id = $id," +
                "   name = $name," +
                "   releaseDate = $releaseDate," +
                "   completionDate = $completionDate," +
                "   episodeQtd = $episodeQtd," +
                "   episodeQtd = $episodeQtd," +
                "   imagePath = $imagePath," +
                ")"
    }
}
