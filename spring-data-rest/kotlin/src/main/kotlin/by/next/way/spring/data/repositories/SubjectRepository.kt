package by.next.way.spring.data.repositories

import by.next.way.spring.data.models.Subject
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RestResource

interface SubjectRepository : PagingAndSortingRepository<Subject, Long> {

    @RestResource(path = "nameContains")
    fun findByNameContaining(@Param("name") name: String, p: Pageable): Page<Subject>

}