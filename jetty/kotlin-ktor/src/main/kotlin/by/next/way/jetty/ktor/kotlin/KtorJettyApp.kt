package by.next.way.jetty.ktor.kotlin

import by.next.way.jetty.ktor.kotlin.route.HelloRoutes
import by.next.way.jetty.ktor.kotlin.route.TestRoutes
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.routing.routing
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.jetty.Jetty

object KtorJettyApp {

    @JvmStatic
    fun main(args: Array<String>) {
        embeddedServer(Jetty, commandLineEnvironment(args)).start(wait = true)
    }

    fun Application.main() {
        // This adds automatically Date and Server headers to each response, and would allow you to configure
        // additional headers served to each response.
        install(DefaultHeaders)
        // This uses use the logger to log every call (request/response)
        install(CallLogging)
        // Registers routes
        routing {
            HelloRoutes(this@main)
            TestRoutes(this@main)
        }
    }
}

