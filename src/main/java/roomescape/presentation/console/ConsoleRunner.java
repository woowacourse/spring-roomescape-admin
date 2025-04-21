package roomescape.presentation.console;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "room-escape-console", havingValue = "enable")
public class ConsoleRunner implements ApplicationRunner {

    private final ConsoleController consoleController;

    public ConsoleRunner(final ConsoleController consoleController) {
        this.consoleController = consoleController;
    }

    @Override
    public void run(final ApplicationArguments args) {
        Thread consoleThread = new Thread(consoleController::run);
        consoleThread.setDaemon(false);
        consoleThread.start();
    }
}
