package com.dietrich.plugins

import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long,
    val name: String,
    val username: String,
    val active: Boolean,
    val points: Int,
)

val users = listOf(
    User(1L, "Rafael", "rafael.ldietrich@gmail.com", true, 23),
    User(2L, "Neves", "neves@ufpr.br", false, 10),
    User(3L, "João", "joao@ufpr.br", false, 20)
).associateBy { it.id }

fun Application.configureRouting() {

    routing {
        get {
            call.respondText("Hello World!")
        }

        get("{id}") {
            val id = call.parameters["id"]!!.toLong()
            val user = fetchUser(id) ?: run {
                call.respond(HttpStatusCode.NotFound, "User not found for id: $id")
                throw Exception()
            }
            call.respond(user)
        }

        get("{id}/points") {
            val id = call.parameters["id"]!!.toLong()
            val user = fetchUser(id) ?: run {
                call.respond(HttpStatusCode.NotFound, "User not found for id: $id")
                throw Exception()
            }

            call.respond(user.points)
        }
    }
}

private fun fetchUser(id: Long): User? {
    return users[id]
}
