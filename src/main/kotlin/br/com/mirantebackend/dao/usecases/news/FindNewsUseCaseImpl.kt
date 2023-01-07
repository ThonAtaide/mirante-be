package br.com.mirantebackend.dao.usecases.news

import br.com.mirantebackend.dao.usecases.interfaces.news.FindNewsUseCase
import br.com.mirantebackend.model.documents.NewsDocument
import org.springframework.data.domain.Page
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service
import java.util.*

@Service
class FindNewsUseCaseImpl: FindNewsUseCase {

    override fun findById(newsId: String, mongoTemplate: MongoTemplate): Optional<NewsDocument> {
        TODO("Not yet implemented")
    }

    override fun findByAll(pageSize: Int, pageNumber: Int, mongoTemplate: MongoTemplate): Page<NewsDocument> {
        TODO("Not yet implemented")
    }
}