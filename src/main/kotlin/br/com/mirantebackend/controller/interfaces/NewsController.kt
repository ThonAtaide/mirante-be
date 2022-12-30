package br.com.mirantebackend.controller.interfaces

import br.com.mirantebackend.dto.pageable.PageDto
import br.com.mirantebackend.controller.vo.news.NewsRequestVo
import br.com.mirantebackend.controller.vo.news.NewsResponseVo
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RequestMapping("/news")
@Tag(name = "NewsController", description = "Operations about news showed into feed")
interface NewsController {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getNews(@RequestParam pageSize: Long, @RequestParam pageNumber: Long): PageDto<NewsResponseVo>

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{newsId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getNews(@PathVariable("newsId") newsId: Long): NewsResponseVo

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createNews(@RequestBody news: NewsRequestVo)

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun updateNews(@RequestBody news: NewsRequestVo)
}