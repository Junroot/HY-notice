package hynotice.core.configuration;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class ExceptionTracer {

    private static final Logger LOGGER = LogManager.getLogger(ExceptionTracer.class);

    public String getStackTrace(final Exception exception) {
        String trace = "";

        try (StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter, true)) {
            exception.printStackTrace(printWriter);
            trace = stringWriter.getBuffer().toString();
        } catch (IOException e) {
            LOGGER.error("스택 트레이스를 확인하는 도중 문제가 발생했습니다. {}", exception.getClass());
        }

        return trace;
    }
}
