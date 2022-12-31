package br.com.mirantebackend.repositories

import br.com.mirantebackend.model.ChampionshipDocument
import org.springframework.data.mongodb.repository.MongoRepository

interface ChampionshipRepository : MongoRepository<ChampionshipDocument, String>