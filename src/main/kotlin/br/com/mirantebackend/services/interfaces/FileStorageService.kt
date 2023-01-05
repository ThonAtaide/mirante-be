package br.com.mirantebackend.services.interfaces

import org.springframework.core.io.Resource
import org.springframework.web.multipart.MultipartFile

interface FileStorageService {

    fun saveNewFile(file: MultipartFile): String

    fun downloadFile(filePath: String): Resource

    fun deleteFile(filePath: String)
}