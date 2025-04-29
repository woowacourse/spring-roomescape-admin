package roomescape.console;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import roomescape.console.controller.ConsoleController;

@Profile("console")
@Component
public class ConsoleApplication implements CommandLineRunner {

    private final ConsoleController consoleController;

    public ConsoleApplication(ConsoleController consoleController) {
        this.consoleController = consoleController;
    }

    @Override
    public void run(String... args) {
        consoleController.run();
    }
}
