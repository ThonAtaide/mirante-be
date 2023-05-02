package br.com.mirantebackend.config

import br.com.mirantebackend.filters.HeaderAppenderFilter
import com.nimbusds.jose.jwk.source.ImmutableSecret
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val customAuthenticationEntryPoint: CustomAuthenticationEntryPoint,
    private val userDetailsService: UserDetailsService
) {

    private val secret = "s/4KMb61LOrMYYAn4rfaQYSgr+le5SMrsMzKw8G6bXc="
//    @Value("\${jwt.public.key}")
//    private lateinit var rsaPrivateKey: RSAPrivateKey
//
//    @Value("\${jwt.public.key}")
//    private lateinit var rsaPublicKey: RSAPublicKey

//    @Bean
//    fun bearerTokenAuthenticationFilter(authenticationManager: AuthenticationManager):BearerTokenAuthenticationFilter {
//        return BearerTokenAuthenticationFilter(authenticationManager)
//    }

    @Bean
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {

        httpSecurity.cors().and().csrf().disable()

        httpSecurity.exceptionHandling { errorHandler ->
            errorHandler
                .authenticationEntryPoint(customAuthenticationEntryPoint)
        }

        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        httpSecurity
            .authorizeRequests()
            .antMatchers("/actuator/**").permitAll()
            .antMatchers("/auth/**").permitAll()
            .antMatchers(HttpMethod.GET, "/championship/**").permitAll()
            .antMatchers(HttpMethod.GET, "/match/**").permitAll()
            .antMatchers(HttpMethod.GET, "/news/**").permitAll()
            .antMatchers(HttpMethod.PUT, "/championship/**", "/match/**", "/news/**").hasRole("ADMIN")
            .antMatchers(HttpMethod.POST, "/championship/**", "/match/**", "/news/**").hasRole("ADMIN")
            .antMatchers(HttpMethod.DELETE, "/championship/**", "/match/**", "/news/**").hasRole("ADMIN")
            .anyRequest().authenticated()
            .and()
            .httpBasic(Customizer.withDefaults())
            .oauth2ResourceServer(OAuth2ResourceServerConfigurer<*>::jwt)
        httpSecurity.addFilterAfter(HeaderAppenderFilter(), BearerTokenAuthenticationFilter::class.java)

        return httpSecurity.build()
    }

    @Bean
    fun jwtAuthenticationConverter(): JwtAuthenticationConverter? {
        val jwtGrantedAuthoritiesConverter = JwtGrantedAuthoritiesConverter()
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles")
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("")
        val jwtAuthenticationConverter = JwtAuthenticationConverter()
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter)
        return jwtAuthenticationConverter
    }

    @Bean
    fun corsFilter(): CorsFilter? {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.addAllowedOrigin("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(
        authenticationConfiguration: AuthenticationConfiguration
    ): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun jwtEncoder(): JwtEncoder { // todo REVER encoder
        val key: SecretKey = SecretKeySpec(secret.encodeToByteArray(), "HmacSHA256")
        val immutableSecret: JWKSource<SecurityContext> = ImmutableSecret(key)
        return NimbusJwtEncoder(immutableSecret)
    }

    @Bean
    fun jwtDecoder(): JwtDecoder {
        val originalKey: SecretKey = SecretKeySpec(secret.encodeToByteArray(), "HmacSHA256")
        return NimbusJwtDecoder.withSecretKey(originalKey).build()
    }
}
