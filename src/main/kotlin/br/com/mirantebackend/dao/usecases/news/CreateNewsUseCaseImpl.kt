package br.com.mirantebackend.dao.usecases.news

import br.com.mirantebackend.dao.usecases.interfaces.news.CreateNewsUseCase
import br.com.mirantebackend.model.documents.NewsDocument
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service

@Service
class CreateNewsUseCaseImpl: CreateNewsUseCase {

    override fun createNews(news: NewsDocument, mongoTemplate: MongoTemplate): NewsDocument {
        TODO("Not yet implemented")
    }
}