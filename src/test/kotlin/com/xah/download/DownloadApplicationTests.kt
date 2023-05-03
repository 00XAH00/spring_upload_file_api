package com.xah.download

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths


@SpringBootTest(classes = [DownloadApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
class DownloadControllerTest(@LocalServerPort port: String) : StringSpec() {

    private val fileName: String = "file.txt"

    init {
        RestAssured.port = port.toInt()
        RestAssured.baseURI = "http://localhost"

        "send file to server" {
            val byteArrayOutputStream = ByteArrayOutputStream()
            val printStream = PrintStream(byteArrayOutputStream, true, StandardCharsets.UTF_8.name())
            for (i in 1..3 ) {
                printStream.println("${i}) line")
            }

            given()
                .multiPart("file", fileName, byteArrayOutputStream.toByteArray())
            .`when`()
                .post("/upload")
            .then()
                .statusCode(201)
        }

        "test existing file after upload" {
            val path = Paths.get("./user_files/${fileName}")
            Files.exists(path) shouldBe true

        }
    }

}
