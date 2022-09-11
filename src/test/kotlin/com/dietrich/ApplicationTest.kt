package com.dietrich

import com.dietrich.plugins.configureRouting
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
            configureRouting()
        }
        val response = client.get("/")
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("Hello World!", response.bodyAsText())
    }

    @Test
    fun `fetch points for active user`() = testApplication {
        val response = client.get("/1/points")
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(23, response.body())
    }

    @Test
    fun `fetch points for inactive user`() = testApplication {
        val response = client.get("/2/points")
        assertEquals(HttpStatusCode.BadRequest, response.status)
    }
}