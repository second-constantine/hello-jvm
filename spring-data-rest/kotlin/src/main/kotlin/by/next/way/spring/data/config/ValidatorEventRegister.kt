package by.next.way.spring.data.config

import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener
import org.springframework.validation.Validator

@Configuration
class ValidatorEventRegister : InitializingBean {

    @Autowired
    internal var validatingRepositoryEventListener: ValidatingRepositoryEventListener? = null

    @Autowired
    private val validators: Map<String, Validator>? = null

    @Throws(Exception::class)
    override fun afterPropertiesSet() {
        val events = listOf("beforeCreate", "afterCreate", "beforeSave", "afterSave", "beforeLinkSave", "afterLinkSave", "beforeDelete", "afterDelete")

        for ((key, value) in validators!!) {
            events.stream().filter { p -> key.startsWith(p) }.findFirst().ifPresent { p -> validatingRepositoryEventListener!!.addValidator(p, value) }
        }
    }
}
