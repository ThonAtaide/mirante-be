package br.com.mirantebackend.services

import br.com.mirantebackend.dao.interfaces.NewsDao
import br.com.mirantebackend.exceptions.NewsNotFoundException
import br.com.mirantebackend.mapper.toNewsDto
import br.com.mirantebackend.model.documents.NewsDocument
import br.com.mirantebackend.model.dto.news.NewsDto
import br.com.mirantebackend.model.dto.news.NewsRequestDto
import br.com.mirantebackend.model.dto.pageable.PageDto
import br.com.mirantebackend.services.interfaces.FileStorageService
import br.com.mirantebackend.services.interfaces.NewsService
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime
import java.time.ZoneOffset.UTC
import java.util.*

@Service
class NewsServiceImpl(
    private val fileStorageService: FileStorageService,
    private val newsDao: NewsDao
) : NewsService {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    override fun createNews(news: NewsRequestDto, createdBy: String, image: MultipartFile): NewsDto =
        logger.info { "Creating new post $news" }
            .let { image }
            .let { fileStorageService.saveNewFile(it) }
            .let { NewsDocument(title = news.title, text = news.text, createdBy = createdBy, imagePath = it) }
            .let { newsDao.createNews(it) }
            .toNewsDto()

    override fun updateNews(newsId: String, news: NewsRequestDto, image: MultipartFile?): NewsDto {
        return newsDao.findById(newsId)
            .map {

                val filePath = Optional
                    .ofNullable(image)
                    .map { file -> fileStorageService.saveNewFile(file) }
                    .orElse(it.imagePath)

                it.imagePath = filePath
                it.title = news.title
                it.text = news.text
                it.updatedAt = LocalDateTime.now(UTC)
                return@map newsDao.updateNews(newsId, it)
            }
            .orElseThrow { NewsNotFoundException("News from id $newsId") }
            .toNewsDto()
    }

    override fun getNews(newsId: String): NewsDto {
        return newsDao.findById(newsId)
            .orElseThrow { NewsNotFoundException("News from id $newsId") }
            .toNewsDto()
    }

    override fun getNews(title: String?, pageSize: Int, pageNumber: Int): PageDto<NewsDto> =
        newsDao.findAll(pageNumber, pageSize)
            .map { it.toNewsDto() }
            .let { PageDto(it.pageable.pageSize, it.pageable.pageNumber, it.totalElements, it.content) }
}