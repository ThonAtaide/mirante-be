package br.com.mirantebackend.services.interfaces

import br.com.mirantebackend.model.dto.news.NewsDto
import br.com.mirantebackend.model.dto.news.NewsRequestDto
import br.com.mirantebackend.model.dto.pageable.PageDto

interface NewsService {

    fun createNews(news: NewsRequestDto): NewsDto

    fun updateNews(newsId: Long, news: NewsRequestDto): NewsDto

    fun getNews(newsId: Long): NewsDto

    fun getNews(title: String? = null, pageSize: Long, pageNumber: Long): PageDto<NewsDto>
}