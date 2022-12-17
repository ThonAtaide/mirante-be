package br.com.mirantebackend.repositories

import br.com.mirantebackend.model.MatchDocument
import org.springframework.data.mongodb.repository.MongoRepository

interface MatchRepository: MongoRepository<MatchDocument, String>