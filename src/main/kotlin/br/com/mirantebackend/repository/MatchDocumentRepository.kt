package br.com.mirantebackend.repository

import br.com.mirantebackend.model.documents.MatchDocument
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MatchDocumentRepository : MongoRepository<MatchDocument, String> {

    @Query("{ 'id' : ?0, 'championship.id': ?1 }")
    fun findByIdAndChampionship(id: String, championship: String): Optional<MatchDocument>
}
