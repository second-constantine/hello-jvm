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
    private Logger log = LogManager.getLogger();

    List<Record> execute(
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
}
