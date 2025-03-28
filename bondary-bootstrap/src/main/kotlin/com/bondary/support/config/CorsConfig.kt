package com.bondary.support.config

import com.bondary.support.auth.AuthProviderArgumentResolver
import com.bondary.support.auth.RegisterTokenArgumentResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfig(
    private val authProviderArgumentResolver: AuthProviderArgumentResolver,
    private val registerTokenArgumentResolver: RegisterTokenArgumentResolver
//    @Value("\${spring.app.cors.allowed-origins}") private val allowedOrigins: String
) : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
//            .allowedOrigins(*allowedOrigins.split(",").toTypedArray())
            .allowedOrigins("http://localhost:3000", "http://localhost:8080")
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .allowedHeaders("*")
    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(authProviderArgumentResolver)
        resolvers.add(registerTokenArgumentResolver)
    }
}