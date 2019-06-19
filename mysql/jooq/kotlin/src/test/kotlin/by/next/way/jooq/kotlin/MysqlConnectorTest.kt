package by.next.way.jooq.kotlin

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Disabled

@Disabled
class MysqlConnectorTest {

    @Test
    fun invoke() {
        val result = MysqlConnector.execute(
                sql = "select * from user_account limit 1;",
                url = "jdbc:mysql://localhost:3306/your_db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                user = "root",
                password = "password"
        )
        Assertions.assertNotNull(result)
    }
}