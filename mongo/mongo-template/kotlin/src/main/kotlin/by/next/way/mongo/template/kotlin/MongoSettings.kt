package by.next.way.mongo.template.kotlin

data class MongoSettings(
        val name: String,
        val hosts: List<String>,
        val credentials: List<String> = arrayListOf(),
        val idleTime: Int = 60000
)