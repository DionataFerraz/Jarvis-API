package br.com.jarvis.domain.entity

import org.hibernate.annotations.GenericGenerator
import java.time.LocalDate
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.validation.constraints.NotEmpty

@Entity
@Table(name = "volume")
data class Volume(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", unique = true, nullable = false)
    val id: UUID,

/*
    @ManyToOne
    @JoinColumn(name = "id_comic_book_locale", referencedColumnName = "id")
    val comicBookLocale: ComicBookLocale,
*/

    @Column(name = "release_date", nullable = false)
    @NotEmpty(message = "Volume release date field is required.")
    val releaseDate: LocalDate,

    @NotEmpty(message = "Volume number field is required.")
    @Column(name = "volume_number", nullable = false)
    val volumeNumber: Int,

    @Column(name = "description", length = 100, nullable = false)
    @NotEmpty(message = "Volume description field is required.")
    val description: String,
)
