package com.bondary.config

import com.mongodb.reactivestreams.client.MongoClient
import org.springframework.boot.autoconfigure.mongo.MongoProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@Configuration
@EnableReactiveMongoRepositories(basePackages = ["com.bondary.repository"])
@EnableMongoAuditing
class MongoDBConfig {
    @Bean
    fun reactiveMongoDatabaseFactory(
        mongoClient: MongoClient,
        properties: MongoProperties
    ): SimpleReactiveMongoDatabaseFactory {
        return SimpleReactiveMongoDatabaseFactory(mongoClient, properties.database)
    }

    @Bean
    fun reactiveMongoTemplate(factory: SimpleReactiveMongoDatabaseFactory): ReactiveMongoTemplate {
        return ReactiveMongoTemplate(factory)
    }
}
