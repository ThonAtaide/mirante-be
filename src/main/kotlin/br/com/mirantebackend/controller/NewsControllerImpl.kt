package br.com.mirantebackend.controller

import br.com.mirantebackend.dto.pageable.PageDto
import br.com.mirantebackend.dto.news.NewsRequestDto
import br.com.mirantebackend.dto.news.NewsDto
import br.com.mirantebackend.controller.interfaces.NewsController
import org.springframework.web.bind.annotation.RestController

@RestController
class NewsControllerImpl: NewsController {

    override fun getNews(pageSize: Long, pageNumber: Long): PageDto<NewsDto> {
        TODO("Not yet implemented")
    }

    override fun getNews(newsId: Long): NewsDto {
        TODO("Not yet implemented")
    }

    override fun createNews(news: NewsRequestDto): NewsDto {
        TODO("Not yet implemented")
    }

    override fun updateNews(newsId: Long, news: NewsRequestDto): NewsDto {
        TODO("Not yet implemented")
    }


}