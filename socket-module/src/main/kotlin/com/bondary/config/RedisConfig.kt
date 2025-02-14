package com.bondary.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.core.ReactiveStringRedisTemplate

@Configuration
class RedisConfig {

    @Value("\${redis.host}")
    private lateinit var redisHost: String

    @Value("\${redis.port}")
    private lateinit var redisPort: String

    @Bean
    fun reactiveRedisConnectionFactory(): ReactiveRedisConnectionFactory {
        return LettuceConnectionFactory(redisHost, redisPort.toInt())
    }

    @Bean
    fun reactiveStringRedisTemplate(factory: ReactiveRedisConnectionFactory): ReactiveStringRedisTemplate {
        return ReactiveStringRedisTemplate(factory)
    }
}
