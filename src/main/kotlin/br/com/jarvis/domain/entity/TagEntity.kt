package br.com.jarvis.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import jakarta.validation.constraints.NotEmpty

@Entity
@Table(name = "tag")
class TagEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    var id: Long? = null,
    @Column(name = "name", length = 50, nullable = false)
    @NotEmpty(message = "Tag name field is required.")
    var name: String,
    @ManyToMany(mappedBy = "tags")
    var comicBooks: Set<ComicBookEntity> = emptySet()
)
