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
        newsService.createNews(news, "Tester")

    override fun updateNews(newsId: String, news: NewsRequestDto): NewsDto =
        newsService.updateNews(newsId, news)

    override fun getNews(newsId: String): NewsDto =
        newsService.getNews(newsId)


    override fun getNews(title: String?, pageSize: Int, pageNumber: Int): PageDto<NewsDto> =
        newsService.getNews(title, pageSize, pageNumber)
}