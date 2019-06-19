package by.next.way.jooq.kotlin

import org.apache.logging.log4j.LogManager
import org.jooq.Record
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import java.sql.DriverManager

object MysqlConnector {

    private val log = LogManager.getLogger()

    fun execute(
            sql: String,
            url: String,
            user: String,
            password: String
    ): List<Record>? {
        Class.forName("com.mysql.cj.jdbc.Driver")
        try {
            DriverManager.getConnection(url, user, password).use { connection ->
                val dslContext = DSL.using(connection, SQLDialect.MYSQL)
                return dslContext.fetchMany(sql).resultsOrRows()[0].result().toList()
            }
        } catch (e: Exception) {
            log.error("Jooq error!", e)
            return null
        }
    }
}