package br.com.mirantebackend.services.interfaces

import br.com.mirantebackend.model.dto.news.NewsDto
import br.com.mirantebackend.model.dto.news.NewsRequestDto
import br.com.mirantebackend.model.dto.pageable.PageDto

interface NewsService {

    fun createNews(news: NewsRequestDto, createdBy: String): NewsDto

    fun updateNews(newsId: String, news: NewsRequestDto): NewsDto

    fun getNews(newsId: String): NewsDto

    fun getNews(title: String? = null, pageSize: Int, pageNumber: Int): PageDto<NewsDto>
}