package roomescape.console;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
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
