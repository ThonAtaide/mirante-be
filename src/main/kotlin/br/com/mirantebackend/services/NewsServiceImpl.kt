package br.com.mirantebackend.services

import br.com.mirantebackend.model.dto.news.NewsRequestDto
import br.com.mirantebackend.model.dto.news.NewsDto
import br.com.mirantebackend.model.dto.pageable.PageDto
import br.com.mirantebackend.services.interfaces.NewsService
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class NewsServiceImpl(
    private val fileStorageServiceImpl: FileStorageServiceImpl
): NewsService {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    override fun createNews(news: NewsRequestDto): NewsDto {
        TODO("Not yet implemented")
    }

    override fun updateNews(newsId: Long, news: NewsRequestDto): NewsDto {
        TODO("Not yet implemented")
    }

    override fun getNews(newsId: Long): NewsDto {
        TODO("Not yet implemented")
    }

    override fun getNews(title: String?, pageSize: Long, pageNumber: Long): PageDto<NewsDto> {
        TODO("Not yet implemented")
    }
}