package br.com.mirantebackend.services

import br.com.mirantebackend.services.interfaces.FileStorageService
import mu.KotlinLogging
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import kotlin.io.path.isRegularFile
import kotlin.io.path.pathString

@Service
class FileStorageServiceImpl : FileStorageService {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    private val uploadsFolderPath: Path = Paths.get("resources/uploads/unsecure")

    init {
        runCatching {
            Files.createDirectories(uploadsFolderPath)
        }
            .onFailure { logger.error { "Error creating uploads folder: ${it.message}" } }
            .onSuccess {
                logger.info { "Created folder uploads successfully: ${it.fileName}" }
            }
    }

    override fun saveNewFile(file: MultipartFile): String {
        try {
            logger.info { "uploading file ${file.originalFilename} to ${uploadsFolderPath.fileName} folder" }

            val uploadedTargetFilePath = uploadsFolderPath.resolve(UUID.randomUUID().toString())
            Files.copy(file.inputStream, uploadedTargetFilePath)
            uploadedTargetFilePath.isRegularFile()
            return uploadedTargetFilePath.pathString
        } catch (err: Exception) {
            logger.error { "Error uploading file ${file.originalFilename} reason: ${err.javaClass}" }
            throw err
        }
    }

    override fun downloadFile(filePath: String): Resource {
        logger.info { "Downloading file $filePath from ${uploadsFolderPath.fileName} folder" }
        val file: Path = uploadsFolderPath.resolve(filePath)

        val resource: Resource = UrlResource(file.toUri())
        if (resource.exists() || resource.isReadable)
            return resource
        else throw FileNotFoundException("The file does not exist or is not readable!")
    }

    override fun deleteFile(filePath: String) {
        logger.info { "Deleting file $filePath from ${uploadsFolderPath.fileName} folder" }
        val file: Path = uploadsFolderPath.resolve(filePath)

        if (!Files.deleteIfExists(file)) {
            throw FileNotFoundException("The file does not exist or is not readable!")
        }
    }
}