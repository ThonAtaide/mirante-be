package br.com.mirantebackend.repositories

import br.com.mirantebackend.model.NewsDocument
import org.springframework.data.mongodb.repository.MongoRepository

interface NewsRepository : MongoRepository<NewsDocument, String>