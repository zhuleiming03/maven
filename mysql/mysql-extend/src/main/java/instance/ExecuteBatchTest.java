package instance;

import util.JdbcUtil;

import java.sql.Connection;
import java.sql.Statement;

public class ExecuteBatchTest {

    public static void main(String[] args) {
        try (Connection connection = JdbcUtil.getConnection();
             Statement statement = connection.createStatement();) {

            connection.setAutoCommit(false);

            statement.addBatch("drop table if exists t_transaction_disposable;");
            statement.addBatch("create table `t_transaction_disposable` (" +
                    "`id` int not null auto_increment primary key," +
                    "`name` varchar(10) not null default ''," +
                    "`amount` decimal(10,2) not null default 0" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;");

            statement.executeBatch();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
