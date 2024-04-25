package roomescape.console;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import roomescape.console.controller.MainConsoleController;

@Component
@Profile("!test")
public class ConsoleRunner implements CommandLineRunner {

    private final MainConsoleController mainConsoleController;

    public ConsoleRunner(MainConsoleController mainConsoleController) {
        this.mainConsoleController = mainConsoleController;
    }

    @Override
    public void run(String... args) throws Exception {
        mainConsoleController.run();
    }
}
