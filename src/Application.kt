package com.cabratech.canopus.api

import com.mongodb.MongoClientOptions
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import com.cabratech.canopus.data.mongo.MongoDataService
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.content.*
import io.ktor.http.content.*
import com.fasterxml.jackson.databind.*
import com.mongodb.MongoClient
import io.ktor.jackson.*
import io.ktor.features.*
import io.ktor.client.*
import io.ktor.client.engine.jetty.*
import org.bson.types.ObjectId

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

private val mongoDataService = MongoDataService(
    MongoClient(),
    "dev"
)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    val client = HttpClient(Jetty) {
    }

    routing {
        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        // Static feature. Try to access `/static/ktor_logo.svg`
        static("/static") {
            resources("static")
        }

        get("/json/jackson") {
            call.respond(mapOf("hello" to "world"))
        }

        get ("/mongo/example"){
            call.respond(
                mongoDataService.allFromCollection("col")
            )
        }

        post ("mongo/example"){
            val documentAsString = call.receiveText()
            val oidOrErrorMessage =
                mongoDataService.saveNewDocument("col", documentAsString)
            if (ObjectId.isValid(oidOrErrorMessage)) {
                call.respond(HttpStatusCode.Created, oidOrErrorMessage)
            } else {
                call.respond(HttpStatusCode.BadRequest, oidOrErrorMessage)
            }
        }

        get("/{id}") {
            val id: String? = call.parameters["id"]
            val document = mongoDataService.getDocumentById("col", id)
            if (document != null) {
                call.respond(document)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        patch("/{id}") {
            val id: String? = call.parameters["id"]
            val documentAsString = call.receiveText()
            val (updatedRecords, message) =
                mongoDataService.updateExistingDocument("col", id, documentAsString)
            when (updatedRecords) {
                -1 -> call.respond(HttpStatusCode.BadRequest, message)
                0 -> call.respond(HttpStatusCode.NotFound, message)
                1 -> call.respond(HttpStatusCode.NoContent)
            }
        }

        delete("/{id}") {
            val id: String? = call.parameters["id"]
            val (updatedRecords, message) =
                mongoDataService.deleteDocument("col", id)
            when (updatedRecords) {
                0 -> call.respond(HttpStatusCode.NotFound, message)
                1 -> call.respond(HttpStatusCode.NoContent)
            }
        }
    }
}

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()