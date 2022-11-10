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
@Table(name = "animation")
class AnimationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    var id: Long? = null

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_comic_book", referencedColumnName = "id")
    var comicBook: ComicBook? = null

    @Column(name = "release_date", nullable = false)
    @NotNull(message = "Animation release date field is required.")
    lateinit var releaseDate: Date

    @Column(name = "episode_qtd", nullable = false)
    @NotNull(message = "Animation episode qtd field is required.")
    var episodeQtd: Int = 0

    @Column(name = "season_qtd", nullable = false)
    @NotNull(message = "Animation season qtd field is required.")
    var seasonQtd: Int = 0

    constructor() {}

    constructor(
        comicBook: ComicBook? = null,
        releaseDate: Date,
        episodeQtd: Int,
        seasonQtd: Int
    ) {
        this.comicBook = comicBook
        this.releaseDate = releaseDate
        this.episodeQtd = episodeQtd
        this.seasonQtd = seasonQtd
    }

    override fun toString(): String {
        return "AnimationEntity(" +
                "   id = $id," +
                "   releaseDate = $releaseDate," +
                "   episodeQtd = $episodeQtd," +
                "   episodeQtd = $episodeQtd," +
                ")"
    }
}
