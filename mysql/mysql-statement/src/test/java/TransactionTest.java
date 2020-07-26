import org.junit.Test;

public class TransactionTest {

    @Test
    public void main() {
        new Account().tranfer("张三", "李四", 100f);
    }
}
