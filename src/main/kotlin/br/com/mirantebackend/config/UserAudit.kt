package br.com.mirantebackend.config

import br.com.mirantebackend.model.documents.NewsDocument.EmbeddedUser
import br.com.mirantebackend.repository.UserDocumentRepository
import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class UserAudit(
    private val userDocumentRepository: UserDocumentRepository
) : AuditorAware<EmbeddedUser> {

    override fun getCurrentAuditor(): Optional<EmbeddedUser> {
        val username = SecurityContextHolder.getContext().authentication.name
        return userDocumentRepository.findByUsername(username)
            .map { EmbeddedUser(it.id!!, it.name) }
    }
}
