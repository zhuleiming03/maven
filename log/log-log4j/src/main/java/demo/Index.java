package demo;

import org.apache.log4j.Logger;

public class Index {

    private static final Logger logger = Logger.getLogger(Index.class);

    public static void main(String[] args) {
        logger.debug("DEBUG 级别日志");
        logger.info("INFO 级别日志");
        logger.warn("WARN 级别日志");
        logger.error("ERROR 级别日志");
    }
}
