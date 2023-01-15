package br.com.mirantebackend.dao.usecases.interfaces.news

import br.com.mirantebackend.model.documents.NewsDocument
import org.springframework.data.mongodb.core.MongoTemplate

interface CreateNewsUseCase {

    fun createNews(news: NewsDocument, mongoTemplate: MongoTemplate): NewsDocument
}
