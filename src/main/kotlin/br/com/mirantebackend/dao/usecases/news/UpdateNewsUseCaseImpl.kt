package br.com.mirantebackend.dao.usecases.news

import br.com.mirantebackend.dao.usecases.interfaces.news.FindNewsUseCase
import br.com.mirantebackend.dao.usecases.interfaces.news.UpdateNewsUseCase
import br.com.mirantebackend.exceptions.NewsNotFoundException
import br.com.mirantebackend.exceptions.NewsUpdateException
import br.com.mirantebackend.model.documents.NewsDocument
import br.com.mirantebackend.model.documents.NewsDocument.Companion.FIELD_ID
import mu.KotlinLogging
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneOffset.UTC

@Service
class UpdateNewsUseCaseImpl(
    private val findNewsUseCase: FindNewsUseCase
) : UpdateNewsUseCase {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    override fun updateNews(newsId: String, news: NewsDocument, mongoTemplate: MongoTemplate): NewsDocument {
        try {
            logger.info { "Updating news from id $newsId" }
            return Query()
                .also { it.addCriteria(Criteria.where(FIELD_ID).`is`(newsId)) }
                .let { query ->
                    val update = Update()
                    update.set(NewsDocument.FIELD_TITLE, news.title)
                    update.set(NewsDocument.FIELD_TEXT, news.text)
                    update.set(NewsDocument.FIELD_IMAGE_PATH, news.imagePath)
                    update.set(NewsDocument.FIELD_UPDATED_AT, LocalDateTime.now(UTC))
                    return@let mongoTemplate.updateFirst(query, update, NewsDocument::class.java)
                }.let { updateResult ->
                    if (updateResult.matchedCount > 1L)
                        throw NewsUpdateException("The number of News that matched with id $newsId was ${updateResult.matchedCount}. However only the first document was updated.")
                    else if (updateResult.matchedCount > 1L)
                        throw NewsUpdateException("No one News matched with id $newsId. So neither document was updated.")
                }.let {
                    findNewsUseCase.findById(newsId, mongoTemplate)
                        .orElseThrow { NewsNotFoundException("News from id $newsId was updated successfully but it was not found.") }
                }
        } catch (err: Exception) {
            logger.error { err.message }
            throw NewsUpdateException(err)
        }
    }
}