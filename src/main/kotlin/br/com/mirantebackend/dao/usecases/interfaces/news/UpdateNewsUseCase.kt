package br.com.mirantebackend.dao.usecases.interfaces.news

import br.com.mirantebackend.model.documents.NewsDocument
import org.springframework.data.mongodb.core.MongoTemplate

interface UpdateNewsUseCase {

    fun updateNews(newsId: String, news: NewsDocument, mongoTemplate: MongoTemplate): NewsDocument
}