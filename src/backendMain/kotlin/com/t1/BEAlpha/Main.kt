package com.t1.BEAlpha

import com.t1.BEAlpha.Db.dbQuery
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.ContentDisposition.Companion.File
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.sessions.*
import io.kvision.remote.applyRoutes
import io.kvision.remote.kvisionInit
import io.kvision.types.KFile
import org.apache.commons.codec.digest.DigestUtils
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import java.io.File

fun Application.main() {
    install(Compression)
    install(DefaultHeaders)
    install(CallLogging)
    Db.init(environment.config)
    routing {
        applyRoutes(CardServiceManager)
        applyRoutes(ImageServiceManager)
    }
    kvisionInit()
}