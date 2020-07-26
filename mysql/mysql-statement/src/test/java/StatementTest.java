import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class StatementTest {

    @Test
    public void executeBatchTest() {
        try (Connection connection = JdbcUtil.getConnection();
             Statement statement = connection.createStatement();) {

            connection.setAutoCommit(false);

            statement.addBatch("drop table if exists t_customer;");
            statement.addBatch("create table `t_customer` (" +
                    "`id` int not null auto_increment primary key," +
                    "`name` varchar(10) null," +
                    "`amount` float null" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;");

            statement.executeBatch();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void executeTest() {
        try (Connection connection = JdbcUtil.getConnection();
             Statement statement = connection.createStatement();) {

            // executeUpdate 返回受影响的行数
            String sql = "insert t_customer(`name`,amount)value('张三',2000),('李四',100);";
            System.out.println("受影响的行数：" + statement.executeUpdate(sql));

            // execute 查询返回 true 操作类返回 false
            sql = "select * from t_customer where `name`='王五';";
            System.out.println("查询：" + statement.execute(sql));
            sql = "insert t_customer(`name`,amount)value('王五',900);";
            System.out.println("插入：" + statement.execute(sql));

            // executeQuery 只能用于只读查询操作 返回结果集
            sql = "select * from t_customer;";
            ResultSet resultSet = statement.executeQuery(sql);

            System.out.println("-----------------------");
            while (resultSet.next()) {
                System.out.println(String.format("id: %s , name: %s , amount: %s  ",
                        resultSet.getObject("id"),
                        resultSet.getObject("amount"),
                        resultSet.getObject("name")));
            }

            resultSet.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
