package br.com.mirantebackend.dao

import br.com.mirantebackend.dao.interfaces.AbstractDao
import br.com.mirantebackend.dao.interfaces.NewsDao
import br.com.mirantebackend.model.dto.news.NewsDto
import br.com.mirantebackend.model.dto.pageable.PageDto
import mu.KotlinLogging
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service

@Service
class NewsDaoImpl(mongoTemplate: MongoTemplate) : AbstractDao(mongoTemplate), NewsDao {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    override fun createNews(newsDto: NewsDto): NewsDto {
        TODO("Not yet implemented")
    }

    override fun updateNews(newsId: Long, newsDto: NewsDto): NewsDto {
        TODO("Not yet implemented")
    }

    override fun findById(newsId: Long): NewsDto {
        TODO("Not yet implemented")
    }

    override fun findAll(title: String, pageNumber: Int, pageSize: Int): PageDto<NewsDto> {
        TODO("Not yet implemented")
    }
}