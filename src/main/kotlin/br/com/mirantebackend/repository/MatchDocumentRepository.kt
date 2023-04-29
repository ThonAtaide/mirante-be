package br.com.mirantebackend.repository

import br.com.mirantebackend.model.documents.MatchDocument
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface MatchDocumentRepository : MongoRepository<MatchDocument, String>
