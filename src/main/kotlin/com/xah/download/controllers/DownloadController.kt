package com.xah.download.controllers
import com.xah.download.services.FileService
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping("/")
class DownloadController(private val fileService: FileService) {

    @PostMapping("/upload")
    fun processFile(@RequestPart file: MultipartFile, response: HttpServletResponse): List<String> {
        response.status = 201
        return fileService.uploadAndSaveFile(file)
    }


}