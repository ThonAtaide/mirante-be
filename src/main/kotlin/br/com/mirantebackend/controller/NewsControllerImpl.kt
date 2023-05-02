package br.com.mirantebackend.controller

import br.com.mirantebackend.controller.interfaces.NewsController
import br.com.mirantebackend.controller.mappers.toNewsRequestDto
import br.com.mirantebackend.controller.mappers.toNewsVo
import br.com.mirantebackend.controller.vo.news.NewsRequestVo
import br.com.mirantebackend.controller.vo.news.NewsVo
import br.com.mirantebackend.model.dto.pageable.PageDto
import br.com.mirantebackend.services.interfaces.NewsService
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.Optional

@RestController
class NewsControllerImpl(
    private val newsService: NewsService
) : NewsController {

    override fun createNews(
        news: NewsRequestVo,
        image: MultipartFile
    ): NewsVo =
        news.toNewsRequestDto()
            .let { newsService.createNews(it, image) }
            .toNewsVo()

    override fun updateNews(
        newsId: String,
        news: NewsRequestVo,
        image: MultipartFile?
    ): NewsVo =
        news.toNewsRequestDto()
            .let { newsService.updateNews(newsId, it, image) }
            .toNewsVo()

    override fun getNews(newsId: String): NewsVo =
        newsService.getNews(newsId).toNewsVo()

    override fun getNews(title: String?, pageSize: Int, pageNumber: Int): PageDto<NewsVo> =
        newsService.getNews(title, pageSize, pageNumber)
            .let { pageable ->
                val data = Optional.ofNullable(pageable.records)
                    .map { content ->
                        content.stream()
                            .map { it.toNewsVo() }.toList()
                    }.orElse(emptyList())
                return@let PageDto<NewsVo>(pageable.pageSize, pageable.pageNumber, pageable.total, data)
            }
}
