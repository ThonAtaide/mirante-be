package br.com.mirantebackend.repositories

import br.com.mirantebackend.model.ChampionshipDocument
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface ChampionshipRepository : MongoRepository<ChampionshipDocument, String>,
    QuerydslPredicateExecutor<ChampionshipDocument> {


}