package br.com.mirantebackend.dao.interfaces

import br.com.mirantebackend.model.documents.NewsDocument
import org.springframework.data.domain.Page
import java.util.*

interface NewsDao {

    fun createNews(newsDocument: NewsDocument): NewsDocument

    fun updateNews(newsId: String, newsDocument: NewsDocument): NewsDocument

    fun findById(newsId: String): Optional<NewsDocument>

    fun findAll(pageNumber: Int, pageSize: Int): Page<NewsDocument>
}