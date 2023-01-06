package br.com.mirantebackend.controller

import br.com.mirantebackend.controller.interfaces.NewsController
import br.com.mirantebackend.model.dto.news.NewsDto
import br.com.mirantebackend.model.dto.news.NewsRequestDto
import br.com.mirantebackend.model.dto.pageable.PageDto
import br.com.mirantebackend.services.interfaces.NewsService
import org.springframework.web.bind.annotation.RestController

@RestController
class NewsControllerImpl(
    private val newsService: NewsService
) : NewsController {

    override fun createNews(news: NewsRequestDto): NewsDto =
        newsService.createNews(news)

    override fun updateNews(newsId: Long, news: NewsRequestDto): NewsDto =
        newsService.updateNews(newsId, news)

    override fun getNews(newsId: Long): NewsDto =
        newsService.getNews(newsId)


    override fun getNews(title: String?, pageSize: Long, pageNumber: Long): PageDto<NewsDto> =
        newsService.getNews(title, pageSize, pageNumber)
}