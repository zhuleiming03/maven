package instance;

import mapper.CustomerMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import pojo.Customer;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Index {

    public static void main(String[] args) {
        try {
            // 根据 mybatis-config.xml 配置的信息得到 sqlSessionFactory
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

            // 然后根据 sqlSessionFactory 得到 session
            try (SqlSession sqlSession = sqlSessionFactory.openSession()) {

                CustomerMapper customerMapper = sqlSession.getMapper(CustomerMapper.class);
                List<Customer> customerList = customerMapper.listCustomer();
                for (Customer customer : customerList) {
                    System.out.printf("Id: %s , customerName: %s , amount : %s \n",
                            customer.getId(), customer.getCustomerName(), customer.getAmount());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
