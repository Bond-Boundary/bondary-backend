package com.bondary.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
@EnableRedisRepositories(
    basePackages = ["com.bondary.repository"]
)
class RedisConfig {
    @Bean
    fun redisConnectionFactory(): LettuceConnectionFactory {
        return LettuceConnectionFactory()
    }

    @Bean
    fun redisTemplate(redisConnectionFactory: LettuceConnectionFactory): RedisTemplate<String, Any> {
        val template = RedisTemplate<String, Any>()
        template.connectionFactory = redisConnectionFactory

        template.keySerializer = StringRedisSerializer()
        template.hashKeySerializer = StringRedisSerializer()

        template.valueSerializer = GenericJackson2JsonRedisSerializer()
        template.hashValueSerializer = GenericJackson2JsonRedisSerializer()

        return template
    }
}
