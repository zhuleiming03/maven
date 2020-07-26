import org.junit.Test;

public class PreparedTest {

    /**
     * 通过 PreparedStatement 防止 SQL 注入
     */
    @Test
    public void prepareTest() {
        Customer customer = new Customer();

        System.out.println(">>>>>>>>> 正常查询张三详情 ");
        System.out.println("Statement：");
        customer.unsafeQuery("张三");
        System.out.println("PreparedStatement：");
        customer.safeQuery("张三");

        System.out.println("\n>>>>>>>>> SQL注入查询 ");
        System.out.println("Statement：");
        customer.unsafeQuery("xx' or '1'='1");
        System.out.println("PreparedStatement：");
        customer.safeQuery("'xx' or 1=1");
    }
}
