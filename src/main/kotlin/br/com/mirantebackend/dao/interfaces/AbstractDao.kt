package br.com.mirantebackend.dao.interfaces

import org.springframework.data.mongodb.core.MongoTemplate

abstract class AbstractDao(val mongoTemplate: MongoTemplate)