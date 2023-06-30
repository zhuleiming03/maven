import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Index {

    public static void main(String[] args) throws Exception {

        // 1 加载驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 2 数据库连接信息
        String url = "jdbc:mysql://localhost:3306/demo?serverTimezone=Asia/Shanghai&useSSL=true";
        String username = "root";
        String password = "123456";

        // 3 连接数据库
        Connection connection = DriverManager.getConnection(url, username, password);

        // 4 创建执行 SQL 的对象
        Statement statement = connection.createStatement();

        // 5 执行 SQL
        String sql = "select * from t_user where id in (1,2,3)";
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            System.out.println(String.format("id: %s , code: %s , name: %s ",
                    resultSet.getObject("id"),
                    resultSet.getObject("user_code"),
                    resultSet.getObject("user_name")));
        }

        // 6 释放连接
        resultSet.close();
        statement.close();
        connection.close();
    }
}
