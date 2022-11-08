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
import javax.validation.constraints.NotEmpty

@Entity
@Table(name = "comic_book")
data class ComicBook(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", unique = true, nullable = false)
    val id: UUID,

    @Column(name = "comic_type", length = 100, nullable = false)
    @Enumerated(EnumType.STRING)
    @NotEmpty(message = "Comic type field is required.")
    val comicType: ComicType,

    @Column(name = "has_animation", nullable = false)
    @NotEmpty(message = "Comic has animation field is required.")
    val hasAnimation: Boolean = false,

    @JsonIgnore
    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY, orphanRemoval = false)
    val comicBooks: Set<ComicBookLocale> = emptySet()
)
