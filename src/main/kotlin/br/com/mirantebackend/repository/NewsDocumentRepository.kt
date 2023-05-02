package br.com.mirantebackend.repository

import br.com.mirantebackend.model.documents.NewsDocument
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface NewsDocumentRepository : MongoRepository<NewsDocument, String>
