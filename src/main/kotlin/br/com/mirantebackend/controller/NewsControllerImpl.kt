package br.com.mirantebackend.controller

import br.com.mirantebackend.dto.pageable.PageDto
import br.com.mirantebackend.controller.vo.news.NewsRequestVo
import br.com.mirantebackend.controller.vo.news.NewsResponseVo
import br.com.mirantebackend.controller.interfaces.NewsController
import org.springframework.web.bind.annotation.RestController

@RestController
class NewsControllerImpl: NewsController {

    override fun getNews(pageSize: Long, pageNumber: Long): PageDto<NewsResponseVo> {
        TODO("Not yet implemented")
    }

    override fun getNews(newsId: Long): NewsResponseVo {
        TODO("Not yet implemented")
    }

    override fun createNews(news: NewsRequestVo) {
        TODO("Not yet implemented")
    }

    override fun updateNews(news: NewsRequestVo) {
        TODO("Not yet implemented")
    }
}