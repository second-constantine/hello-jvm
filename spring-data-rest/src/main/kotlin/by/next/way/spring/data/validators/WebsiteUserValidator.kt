package by.next.way.spring.data.validators

import by.next.way.spring.data.models.WebsiteUser
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.Validator

@Component("beforeCreateWebsiteUserValidator")
class WebsiteUserValidator : Validator {

    override fun supports(clazz: Class<*>): Boolean {
        return WebsiteUser::class.java == clazz
    }

    override fun validate(obj: Any, errors: Errors) {

        val user = obj as WebsiteUser
        if (checkInputString(user.name)) {
            errors.rejectValue("name", "name.empty")
        }

        if (checkInputString(user.email)) {
            errors.rejectValue("email", "email.empty")
        }
    }

    private fun checkInputString(input: String?): Boolean {
        return input == null || input.trim { it <= ' ' }.isEmpty()
    }
}