import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class logTest {

    private static final Logger logger = LoggerFactory.getLogger(logTest.class);

    public static void main(String[] args) {
        logger.trace("simple TRACE test");
        logger.debug("simple DEBUG test");
        logger.info("simple {} test", "INFO");
        logger.warn("simple {} {}","WARN","test");
        logger.error("simple ERROR test");
    }
}
