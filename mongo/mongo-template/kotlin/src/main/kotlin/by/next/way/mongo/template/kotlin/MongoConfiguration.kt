package by.next.way.mongo.template.kotlin

import com.mongodb.MongoClient
import com.mongodb.MongoClientOptions
import com.mongodb.ServerAddress
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.config.AbstractMongoConfiguration
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.core.convert.MongoCustomConversions
import java.util.stream.Collectors

class MongoConfiguration(
        private val mongoSettings: MongoSettings
) : AbstractMongoConfiguration() {

    override fun getDatabaseName(): String {
        return mongoSettings.name
    }

    protected fun clientOptionsBuilder(): MongoClientOptions.Builder {
        return MongoClientOptions.Builder()
    }

    override fun mongoClient(): MongoClient {
        val clientOptions = clientOptionsBuilder()
                .maxConnectionIdleTime(mongoSettings.idleTime)
                .build()
        val mongoClient = MongoClient(produceAddresses(mongoSettings.hosts), clientOptions)
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

    companion object {
        fun produceAddresses(addresses: List<String>): List<ServerAddress> {
            return addresses.stream().map<ServerAddress>({ ServerAddress(it) })
                    .collect(Collectors.toList())
        }
    }
}