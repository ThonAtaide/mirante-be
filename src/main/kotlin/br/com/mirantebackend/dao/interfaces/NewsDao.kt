package br.com.mirantebackend.dao.interfaces

import br.com.mirantebackend.model.dto.news.NewsDto
import br.com.mirantebackend.model.dto.pageable.PageDto

interface NewsDao {

    fun createNews(newsDto: NewsDto): NewsDto

    fun updateNews(newsId: Long, newsDto: NewsDto): NewsDto

    fun findById(newsId: Long): NewsDto

    fun findAll(title: String, pageNumber: Int, pageSize: Int): PageDto<NewsDto>
}