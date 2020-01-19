package by.next.way.mongo.template.kotlin

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.core.convert.MongoCustomConversions

class MongoConfiguration(
        private val databaseName: String,
        private val host: String
) : AbstractMongoClientConfiguration() {

    override fun getDatabaseName(): String {
        return databaseName
    }

    override fun mongoClient(): MongoClient {
        val mongoClient = MongoClients.create("mongodb://$host")
        checkConnection(mongoClient)
        return mongoClient
    }

    @Bean
    @Throws(Exception::class)
    override fun mappingMongoConverter(): MappingMongoConverter {
        val dbRefResolver = DefaultDbRefResolver(mongoDbFactory())
        val converter = MappingMongoConverter(dbRefResolver, mongoMappingContext())
        converter.setCustomConversions(customConversions())
        return converter
    }

    @Bean
    override fun customConversions(): MongoCustomConversions {
        return MongoCustomConversions(emptyList<Any>())
    }

    protected fun checkConnection(mongoClient: MongoClient) {
        try {
            mongoClient.listDatabaseNames().first()
        } catch (e: Exception) {
            throw IllegalStateException("[MONGO] Connection check failed", e)
        }
    }
}