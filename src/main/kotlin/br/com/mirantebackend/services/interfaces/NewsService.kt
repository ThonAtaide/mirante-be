package br.com.mirantebackend.services.interfaces

import br.com.mirantebackend.dto.news.NewsDto
import br.com.mirantebackend.dto.news.NewsRequestDto
import br.com.mirantebackend.dto.pageable.PageDto

interface NewsService {

    fun createNews(news: NewsRequestDto): NewsDto

    fun updateNews(newsId: Long, news: NewsRequestDto): NewsDto

    fun getNews(newsId: Long): NewsDto

    fun getNews(pageSize: Long, pageNumber: Long): PageDto<NewsDto>
}