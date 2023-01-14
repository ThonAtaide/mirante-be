package br.com.mirantebackend.services.interfaces

import br.com.mirantebackend.model.dto.news.NewsDto
import br.com.mirantebackend.model.dto.news.NewsRequestDto
import br.com.mirantebackend.model.dto.pageable.PageDto
import org.springframework.web.multipart.MultipartFile

interface NewsService {

    fun createNews(news: NewsRequestDto, createdBy: String, image: MultipartFile): NewsDto

    fun updateNews(newsId: String, news: NewsRequestDto, image: MultipartFile? = null): NewsDto

    fun getNews(newsId: String): NewsDto

    fun getNews(title: String? = null, pageSize: Int, pageNumber: Int): PageDto<NewsDto>
}