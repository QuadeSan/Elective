import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestLog {
    private static Logger logger = LogManager.getLogger(TestLog.class);

    public static void main(String[] args) {
        logger.debug("Debug massage test");
        logger.info("Info massage test");
        logger.warn("Warn massage test");
        logger.error("Error massage test");
        logger.fatal("Fatal massage test");
    }
}
