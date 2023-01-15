package br.com.mirantebackend.dao.usecases.interfaces.news

import br.com.mirantebackend.model.documents.NewsDocument
import org.springframework.data.domain.Page
import org.springframework.data.mongodb.core.MongoTemplate
import java.util.Optional
interface FindNewsUseCase {

    fun findById(newsId: String, mongoTemplate: MongoTemplate): Optional<NewsDocument>

    fun findByAll(pageSize: Int, pageNumber: Int, mongoTemplate: MongoTemplate): Page<NewsDocument>
}
