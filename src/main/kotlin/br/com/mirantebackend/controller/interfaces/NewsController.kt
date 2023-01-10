package br.com.mirantebackend.controller.interfaces

import br.com.mirantebackend.controller.vo.NewsRequestVo
import br.com.mirantebackend.controller.vo.NewsVo
import br.com.mirantebackend.model.dto.pageable.PageDto
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
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
    fun createNews(@Validated @ModelAttribute("news") news: NewsRequestVo): NewsVo

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(
        "/{newsId}",
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE
        ]
    )
    fun updateNews(
        @PathVariable("newsId") newsId: String,
        @Validated @ModelAttribute("news") news: NewsRequestVo
    ): NewsVo

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(
        "/{newsId}",
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getNews(@PathVariable("newsId") newsId: String): NewsVo

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getNews(
        @RequestParam("title") title: String? = null,
        @RequestParam(defaultValue = "10") pageSize: Int = 10,
        @RequestParam(defaultValue = "0") pageNumber: Int = 0
    ): PageDto<NewsVo>
}