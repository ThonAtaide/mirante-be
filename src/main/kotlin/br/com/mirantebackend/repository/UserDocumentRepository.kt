package br.com.mirantebackend.repository

import br.com.mirantebackend.model.documents.UserDocument
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserDocumentRepository : MongoRepository<UserDocument, String> {

    fun findByUsername(username: String): Optional<UserDocument>
}

