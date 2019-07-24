package by.next.way.spring.data.config

import by.next.way.spring.data.models.WebsiteUser
import by.next.way.spring.data.projections.CustomBook
import org.springframework.context.annotation.Configuration
import org.springframework.data.rest.core.config.RepositoryRestConfiguration
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer
import org.springframework.http.HttpMethod

@Configuration
class RestConfig : RepositoryRestConfigurer {

    override fun configureRepositoryRestConfiguration(repositoryRestConfiguration: RepositoryRestConfiguration) {
        repositoryRestConfiguration.projectionConfiguration.addProjection(CustomBook::class.java)
        val config = repositoryRestConfiguration.exposureConfiguration
        config.forDomainType(WebsiteUser::class.java).withItemExposure { metadata, httpMethods -> httpMethods.disable(HttpMethod.PATCH) }
    }
}

