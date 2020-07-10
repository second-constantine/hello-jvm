package by.next.way.jooq.java;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class MysqlConnector {
    private static Logger log = LogManager.getLogger();

    public static List<Record> fetchMany(
            String sql,
            String url,
            String user,
            String password
    ) throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);
            return dslContext.fetchMany(sql).resultsOrRows().get(0).result();
        } catch (Exception e) {
            log.error("Jooq error!", e);
            return null;
        }
    }

    public static int execute(
            String sql,
            String url,
            String user,
            String password
    ) throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);
            return dslContext.execute(sql);
        } catch (Exception e) {
            log.error("Jooq error!", e);
            return 0;
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Record credit = MysqlConnector.fetchMany(
                "select * from credit order by id desc limit 1",
                "jdbc:mysql://localhost:3306/your_db",
                "mm_dev",
                "123456"
        ).get(0);
        log.info(credit.get("id"));

        MysqlConnector.execute(
                "update credit set psk = 33.1 where id = " + credit.get("id"),
                "jdbc:mysql://localhost:3306/your_db",
                "mm_dev",
                "123456");

    }
}
