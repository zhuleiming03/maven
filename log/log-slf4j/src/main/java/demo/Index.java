package demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Index {

    private static final Logger logger = LoggerFactory.getLogger(Index.class);

    public static void main(String[] args) {
        logger.trace("simple TRACE test");
        logger.debug("simple DEBUG test");
        logger.info("simple {} test", "INFO");
        logger.warn("simple {} {}", "WARN", "test");
        logger.error("simple ERROR test");
    }
}
