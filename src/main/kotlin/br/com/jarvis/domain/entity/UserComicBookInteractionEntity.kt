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
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull

// TODO: Preciso ver se é melhor separar ou não cada uma das ações dos usuários, em questão de quantidade de dados faz mais sentido remover
@Entity
@Table(name = "user_volume_interaction")
class UserComicBookInteractionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    var id: Long? = null,
    @Column(nullable = false)
    @NotNull(message = "User comic book interaction isFavorite field is required.")
    val isFavorite: Boolean = false,
    @Column(nullable = false)
    @NotNull(message = "User comic book userReview isFavorite field is required.")
    val userReview: Double = 0.0,
    @Column(nullable = false)
    @NotNull(message = "User comic book interaction isReadAll field is required.")
    val isReadAll: Boolean = false,
    @Column(nullable = false)
    @NotNull(message = "User comic book interaction hasAllVolume field is required.")
    val hasAllVolume: Boolean = false,
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_comic_book", referencedColumnName = "id")
    var comicBookEntity: ComicBookEntity? = null,
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    var userEntity: UserEntity? = null
)
