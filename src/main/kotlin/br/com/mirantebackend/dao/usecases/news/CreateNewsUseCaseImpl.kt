package br.com.mirantebackend.dao.usecases.news

import br.com.mirantebackend.dao.usecases.interfaces.news.CreateNewsUseCase
import br.com.mirantebackend.exceptions.NewsCreateException
import br.com.mirantebackend.model.documents.NewsDocument
import mu.KotlinLogging
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service

@Service
class CreateNewsUseCaseImpl : CreateNewsUseCase {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    override fun createNews(news: NewsDocument, mongoTemplate: MongoTemplate): NewsDocument {
        try {
            return logger.info { "Creating a new post $news" }
                .let { mongoTemplate.save(news) }
        } catch (err: Exception) {
            logger.error { err.message }
            throw NewsCreateException(err)
        }
    }
}
