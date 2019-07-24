package by.next.way.spring.data.repositories

import by.next.way.spring.data.models.WebsiteUser
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.data.rest.core.annotation.RestResource
import org.springframework.web.bind.annotation.CrossOrigin

@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "users", path = "users")
interface UserRepository : CrudRepository<WebsiteUser, Long> {

    @RestResource(exported = false)
    override fun delete(entity: WebsiteUser)

    @RestResource(exported = false)
    override fun deleteAll()

    @RestResource(exported = false)
    override fun deleteAll(entities: Iterable<WebsiteUser>)

    @RestResource(exported = false)
    override fun deleteById(aLong: Long)

    @RestResource(path = "byEmail", rel = "customFindMethod")
    fun findByEmail(@Param("email") email: String): WebsiteUser
}
