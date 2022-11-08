package br.com.jarvis.domain.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDate
import java.util.Locale
import java.util.UUID
import javax.persistence.Basic
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.validation.constraints.NotEmpty

@Entity
@Table(name = "comic_book_locale")
data class ComicBookLocale(
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    val id: UUID,

    @ManyToOne
    @JoinColumn(name = "id_comic_book", referencedColumnName = "id")
    val comicBook: ComicBook,

    @Column(name = "name", length = 50, nullable = false)
    @NotEmpty(message = "Comic book locale name field is required.")
    val name: String,

    @Column(name = "description", length = 100, nullable = false)
    @NotEmpty(message = "Comic book locale description field is required.")
    val description: String,

    @Basic(optional = false)
    @Column(name = "locale", nullable = false)
    @NotEmpty(message = "Comic book locale field is required.")
    val locale: Locale,

    @Column(name = "release_date", nullable = false)
    @NotEmpty(message = "Comic book locale release date field is required.")
    val releaseDate: LocalDate,

    @Column(name = "completion_date")
    @JsonIgnore
    val completionDate: LocalDate?,

    @JsonIgnore
    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY, orphanRemoval = false)
    val volumes: Set<Volume> = emptySet()

)
