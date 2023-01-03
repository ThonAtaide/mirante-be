package br.com.mirantebackend.config

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean





@Configuration
@EnableMongoRepositories(basePackages = ["br.com.mirantebackend.repositories"])
class MongoConfig {

    @Value("\${spring.data.mongodb.uri}")
    private lateinit var connectionUrl: String

    @Bean
    fun mongoClient(): MongoClient? {
        val connectionString = ConnectionString(connectionUrl)
        val mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .build()
        return MongoClients.create(mongoClientSettings)
    }

    @Bean
    @Throws(Exception::class)
    fun mongoTemplate(): MongoTemplate? {
        return MongoTemplate(mongoClient()!!, "mirante_db")
    }

    @Bean
    fun validatingMongoEventListener(): ValidatingMongoEventListener? {
        return ValidatingMongoEventListener(validator())
    }

    @Bean
    fun validator(): LocalValidatorFactoryBean {
        return LocalValidatorFactoryBean()
    }
}