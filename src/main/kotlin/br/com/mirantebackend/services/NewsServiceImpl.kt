package br.com.mirantebackend.services

import br.com.mirantebackend.dto.news.NewsRequestDto
import br.com.mirantebackend.dto.news.NewsDto
import br.com.mirantebackend.dto.pageable.PageDto
import br.com.mirantebackend.services.interfaces.NewsService
import mu.KotlinLogging

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

    override fun getNews(pageSize: Long, pageNumber: Long): PageDto<NewsDto> {
        TODO("Not yet implemented")
    }
}