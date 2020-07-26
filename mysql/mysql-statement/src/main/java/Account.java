import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Random;

class Account {

    void tranfer(String receive, String pay, Float amount) {
        try (Connection connection = JdbcUtil.getConnection()) {

            // 关闭自动提交 开启事务
            connection.setAutoCommit(false);

            String sql = "update t_customer set amount=amount+? where `name`=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setFloat(1, amount);
            preparedStatement.setString(2, receive);
            preparedStatement.executeUpdate();

            // 二分之一随机发生异常
            int random = 1 / new Random().nextInt(2);

            sql = "update t_customer set amount=amount-? where `name`=?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setFloat(1, amount);
            preparedStatement.setString(2, pay);
            preparedStatement.executeUpdate();

            connection.commit();
            preparedStatement.close();
            System.out.println("事务成功");

        } catch (Exception e) {
            System.out.println("事务失败 已回滚");
            e.printStackTrace();
        }
    }
}
