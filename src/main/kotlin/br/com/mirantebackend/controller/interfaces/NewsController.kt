package br.com.mirantebackend.controller.interfaces

import br.com.mirantebackend.model.dto.news.NewsDto
import br.com.mirantebackend.model.dto.news.NewsRequestDto
import br.com.mirantebackend.model.dto.pageable.PageDto
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RequestMapping("/news")
@Tag(name = "NewsController", description = "Operations about news showed into feed")
interface NewsController {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE
        ]
    )
    fun createNews(@ModelAttribute("news") news: NewsRequestDto): NewsDto

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(
        "/{newsId}",
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE
        ]
    )
    fun updateNews(@PathVariable("newsId") newsId: Long, @ModelAttribute("news") news: NewsRequestDto): NewsDto

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(
        "/{newsId}",
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getNews(@PathVariable("newsId") newsId: Long): NewsDto

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getNews(
        @RequestParam("title") title: String? = null,
        @RequestParam(defaultValue = "10") pageSize: Long = 10,
        @RequestParam(defaultValue = "0") pageNumber: Long = 0
    ): PageDto<NewsDto>
}