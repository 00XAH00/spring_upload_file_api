package com.xah.download.services
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths


@Service
class FileService {

    private val basePath: String = "./user_files"

    init {
        createPathIfNotExist()
    }

    private final fun createPathIfNotExist() {
        val path = Paths.get(basePath)
        if (!Files.exists(path)) {
            Files.createDirectories(path)
        }
    }

    fun uploadAndSaveFile(file: MultipartFile): List<String> {
        val lines: List<String>


        try {
            lines = file
                .inputStream
                .bufferedReader()
                .readLines()
        } catch (e: IOException) {
            throw (RuntimeException("Ошибка чтения файла"))
        }


        val path = Paths.get("./user_files", file.originalFilename)
        Files.write(path, file.bytes)

        return lines
    }

}