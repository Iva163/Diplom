package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static QueryRunner runner = new QueryRunner();

    public SQLHelper() {
    }

    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection(System.getProperty("db.url"), "app", "pass");

    }

    @SneakyThrows
    public static void cleanDatabase() {
        var connection = getConn();
        runner.execute(connection, "DELETE FROM payment_entity");
        runner.execute(connection, "DELETE FROM credit_request_entity");
        runner.execute(connection, "DELETE FROM order_entity");

    }

    public static String getPaymentCardStatus() {
        var codeSQL = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        try (var conn = getConn()) {
            return runner.query(conn, codeSQL, new ScalarHandler<>());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static String getCreditCardStatus() {
        var codeSQL = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
        try (var conn = getConn()) {
            return runner.query(conn, codeSQL, new ScalarHandler<String>());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static long getOrderCount() {
        var codeSQL = "SELECT COUNT(*) FROM order_entity";
        try (var conn = getConn()) {
            return runner.query(conn, codeSQL, new ScalarHandler<Long>());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public static String getCreditIdFromOrder() {
        var codeSQL = "Select credit_id from order_entity";
        try (var conn = getConn()) {
            return runner.query(conn, codeSQL, new ScalarHandler<String>());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static String getPaymentIdFromOrder() {
        var codeSQL = "Select payment_id from order_entity";
        try (var conn = getConn()) {
            return runner.query(conn, codeSQL, new ScalarHandler<String>());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static int checkIdOrder(String id) {
        if (id == null){
            return 0;
        } else {return 1;}
    }

    public static int getAmount() {
        var codeSQL = "Select amount from payment_entity";
        try (var conn = getConn()) {
            return runner.query(conn, codeSQL, new ScalarHandler<Integer>());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return 0;
    }

}