package br.com.mirantebackend.repository

import br.com.mirantebackend.model.documents.ChampionshipDocument
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ChampionshipDocumentRepository : MongoRepository<ChampionshipDocument, String>
