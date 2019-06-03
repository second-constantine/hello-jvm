package by.next.way.jetty.ktor.kotlin.route

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.html.respondHtml
import io.ktor.routing.get
import io.ktor.routing.routing
import kotlinx.html.body
import kotlinx.html.head
import kotlinx.html.p
import kotlinx.html.title

class HelloRoutes constructor(application: Application) {
    init {
        application.routing {
            get("/") {
                call.respondHtml {
                    head {
                        title { +"Ktor" }
                    }
                    body {
                        p {
                            +"Hello world!"
                        }
                    }
                }
            }
        }
    }
}