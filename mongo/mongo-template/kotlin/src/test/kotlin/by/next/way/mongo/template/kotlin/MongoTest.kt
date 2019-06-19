package by.next.way.mongo.template.kotlin

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.data.mongodb.core.query.Query

@Disabled
class MongoTest {

    @Test
    fun invoke() {
        val mongoConfiguration = MongoConfiguration(MongoSettings(
                name = "test_hello",
                hosts = arrayListOf("localhost:27017")
        ))
        val mongoTemplate = mongoConfiguration.mongoTemplate()
        mongoTemplate.save(HelloWorldData())
        val allInMongo = mongoTemplate.find(Query(), HelloWorldData::class.java)
        Assertions.assertTrue(allInMongo.isNotEmpty())
    }
}