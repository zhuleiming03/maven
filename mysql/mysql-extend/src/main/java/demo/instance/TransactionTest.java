package demo.instance;

import demo.util.JdbcUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Random;

public class TransactionTest {

    public static void main(String[] args) {
        transaction("张三", "李四", new BigDecimal("100"));
    }

    private static void transaction(String receive, String pay, BigDecimal amount) {
        try (Connection connection = JdbcUtil.getConnection()) {

            // 关闭自动提交 开启事务
            connection.setAutoCommit(false);

            String sql = "update t_transaction_disposable set amount=amount+? where `name`=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBigDecimal(1, amount);
            preparedStatement.setString(2, receive);
            preparedStatement.executeUpdate();

            // 二分之一随机发生异常
            int random = 1 / new Random().nextInt(2);

            sql = "update t_transaction_disposable set amount=amount-? where `name`=?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBigDecimal(1, amount);
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
