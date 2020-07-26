import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

class Customer {

    /**
     * 常规查询
     * SQL 注入的风险
     *
     * @param condition
     */
    void unsafeQuery(String condition) {

        try (Connection connection = JdbcUtil.getConnection();
             Statement statement = connection.createStatement();) {

            String sql = String.format("select * from t_customer where `name`='%s';", condition);
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
    void safeQuery(String condition) {

        String sql = "select * from t_customer where `name`=?;";

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
