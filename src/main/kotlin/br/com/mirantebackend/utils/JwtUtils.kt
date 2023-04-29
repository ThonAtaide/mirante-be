package br.com.mirantebackend.utils

import org.springframework.security.core.userdetails.User
import org.springframework.security.oauth2.jwt.JwsHeader
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import java.time.Instant

class JwtUtils() {

    companion object {
        public val BEARER_PREFIX = "Bearer"

        fun buildJwtToken(
            user: User,
            jwtEncoder: JwtEncoder,
            tokenDuration: Long
        ): String = user.let {
            val nowInstant = Instant.now()
            val jwsHeader = JwsHeader.with { "HS256" }.build()
            val claims = JwtClaimsSet.builder()
                .issuer("mirante.be") // todo refatorar depois
                .issuedAt(nowInstant)
                .expiresAt(nowInstant.plusMillis(tokenDuration))
                .subject(it.username)
                .claim("roles", it.authorities)
                .build()
            return@let jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).tokenValue
        }
    }
}
