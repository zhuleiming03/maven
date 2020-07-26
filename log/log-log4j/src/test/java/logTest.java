import org.apache.log4j.Logger;
import org.junit.Test;

public class logTest {

    private static final Logger logger = Logger.getLogger(logTest.class);

    @Test
    public void baseTest() {
        logger.debug("DEBUG 级别日志");
        logger.info("INFO 级别日志");
        logger.warn("WARN 级别日志");
        logger.error("ERROR 级别日志");
    }
}
