package br.com.mirantebackend.dao.interfaces

import org.springframework.data.mongodb.core.MongoTemplate

abstract class AbstractDao(val mongoTemplate: MongoTemplate) {

    companion object {
        @JvmStatic
        protected val REGEX_OPTIONS_CASE_INSENSITIVE = "i"
    }
}