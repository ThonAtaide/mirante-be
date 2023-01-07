package br.com.mirantebackend.dao.usecases.news

import br.com.mirantebackend.dao.usecases.interfaces.news.UpdateNewsUseCase
import br.com.mirantebackend.model.documents.NewsDocument
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service

@Service
class UpdateNewsUseCaseImpl: UpdateNewsUseCase {

    override fun updateNews(newsId: String, news: NewsDocument, mongoTemplate: MongoTemplate): NewsDocument {
        TODO("Not yet implemented")
    }
}