package demo.instance;

import demo.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class PreparedTest {


    public static void main(String[] args) {
        System.out.println(">>>>>>>>> 正常查询张三详情 ");
        System.out.println("Statement：");
        unsafeQuery("张三");
        System.out.println("PreparedStatement：");
        safeQuery("张三");

        System.out.println("\n>>>>>>>>> SQL注入查询 ");
        System.out.println("Statement：");
        unsafeQuery("xx' or '1'='1");
        System.out.println("PreparedStatement：");
        safeQuery("xx' or '1'='1");
    }

    /**
     * 常规查询
     * SQL 注入的风险
     *
     * @param condition
     */
    private static void unsafeQuery(String condition) {

        try (Connection connection = JdbcUtil.getConnection();
             Statement statement = connection.createStatement()) {

            String sql = String.format("select * from t_transaction_disposable where `name`='%s';", condition);
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                System.out.println(String.format("id: %s , name: %s , amount: %s  ",
                        resultSet.getObject("id"),
                        resultSet.getObject("amount"),
                        resultSet.getObject("name")));
            }

            resultSet.close();

        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 防 SQL 注入查询
     *
     * @param condition
     */
    private static void safeQuery(String condition) {

        String sql = "select * from t_transaction_disposable where `name`=?;";

        try (Connection connection = JdbcUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, condition);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                System.out.println(String.format("id: %s , name: %s , amount: %s  ",
                        resultSet.getObject("id"),
                        resultSet.getObject("amount"),
                        resultSet.getObject("name")));
            }

            resultSet.close();

        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }
}
