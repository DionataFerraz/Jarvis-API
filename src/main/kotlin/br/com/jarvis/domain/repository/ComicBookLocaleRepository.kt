package br.com.jarvis.domain.repository

import br.com.jarvis.domain.entity.ComicBook
import br.com.jarvis.domain.entity.ComicBookLocale
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ComicBookLocaleRepository : JpaRepository<ComicBookLocale, Long> {
    fun findByComicBookIn(comicBook: List<ComicBook>): Set<ComicBookLocale>

    //só funciona com lista, caso eu tente colocar apenas um da pau:
    //Error creating bean with name 'comicBookController' defined in file [/Users/dionataferraz/Workspace/pessoal/Jarvis-API/build/classes/kotlin/main/br/com/jarvis/rest/controller/ComicBookController.class]: Unsatisfied dependency expressed through constructor parameter 0; nested exception is org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'comicBookServiceImpl' defined in file [/Users/dionataferraz/Workspace/pessoal/Jarvis-API/build/classes/kotlin/main/br/com/jarvis/service/impl/ComicBookServiceImpl.class]: Unsatisfied dependency expressed through constructor parameter 1; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'comicBookLocaleRepository' defined in br.com.jarvis.domain.repository.ComicBookLocaleRepository defined in @EnableJpaRepositories declared on JpaRepositoriesRegistrar.EnableJpaRepositoriesConfiguration: Invocation of init method failed; nested exception is org.springframework.data.repository.query.QueryCreationException: Could not create query for public abstract java.util.Optional br.com.jarvis.domain.repository.ComicBookLocaleRepository.findByComicBookIn(br.com.jarvis.domain.entity.ComicBook); Reason: Failed to create query for method public abstract java.util.Optional br.com.jarvis.domain.repository.ComicBookLocaleRepository.findByComicBookIn(br.com.jarvis.domain.entity.ComicBook)! Operator IN on comicBook requires a Collection argument, found class br.com.jarvis.domain.entity.ComicBook in method public abstract java.util.Optional br.com.jarvis.domain.repository.ComicBookLocaleRepository.findByComicBookIn(br.com.jarvis.domain.entity.ComicBook).; nested exception is java.lang.IllegalArgumentException: Failed to create query for method public abstract java.util.Optional br.com.jarvis.domain.repository.ComicBookLocaleRepository.findByComicBookIn(br.com.jarvis.domain.entity.ComicBook)! Operator IN on comicBook requires a Collection argument, found class br.com.jarvis.domain.entity.ComicBook in method public abstract java.util.Optional br.com.jarvis.domain.repository.ComicBookLocaleRepository.findByComicBookIn(br.com.jarvis.domain.entity.ComicBook).
    fun findByComicBookIn(comicBook: Set<ComicBook>): Optional<ComicBookLocale>

    fun findByName(name: String?): Set<ComicBookLocale>
}
