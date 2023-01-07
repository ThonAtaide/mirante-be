package br.com.mirantebackend.dao

import br.com.mirantebackend.dao.interfaces.AbstractDao
import br.com.mirantebackend.dao.interfaces.NewsDao
import br.com.mirantebackend.dao.usecases.interfaces.news.CreateNewsUseCase
import br.com.mirantebackend.dao.usecases.interfaces.news.FindNewsUseCase
import br.com.mirantebackend.dao.usecases.interfaces.news.UpdateNewsUseCase
import br.com.mirantebackend.model.documents.NewsDocument
import mu.KotlinLogging
import org.springframework.data.domain.Page
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service
import java.util.*

@Service
class NewsDaoImpl(
    private val createNewsUseCase: CreateNewsUseCase,
    private val updateNewsUseCase: UpdateNewsUseCase,
    private val findNewsUseCase: FindNewsUseCase,
    mongoTemplate: MongoTemplate
) : AbstractDao(mongoTemplate), NewsDao {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    override fun createNews(newsDocument: NewsDocument): NewsDocument =
        logger.info { "Creating a new document $newsDocument" }
            .let { createNewsUseCase.createNews(newsDocument, mongoTemplate) }

    override fun updateNews(newsId: String, newsDocument: NewsDocument): NewsDocument =
        logger.info { "Updating document $newsDocument" }
            .let { updateNewsUseCase.updateNews(newsId, newsDocument, mongoTemplate) }

    override fun findById(newsId: String): Optional<NewsDocument> =
        logger.info { "Finding news from id $newsId" }
            .let { findNewsUseCase.findById(newsId, mongoTemplate) }

    override fun findAll(pageNumber: Int, pageSize: Int): Page<NewsDocument> =
        logger.info { "Finding news" }
            .let { findNewsUseCase.findByAll(pageNumber, pageSize, mongoTemplate) }


}