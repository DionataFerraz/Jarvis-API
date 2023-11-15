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
import java.util.Date

@Entity
@Table(name = "user_interest")
class UserInterestEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    var id: Long? = null,
    @Column(name = "name", length = 50, nullable = false)
    @NotEmpty(message = "User interest date field is required.")
    val date: Date,
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_comic_book", referencedColumnName = "id")
    var comicBookEntity: ComicBookEntity? = null,
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tag", referencedColumnName = "id")
    val tagEntity: TagEntity? = null
)
