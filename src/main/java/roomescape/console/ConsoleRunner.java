package roomescape.console;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import roomescape.console.config.ControllerFactory;
import roomescape.console.controller.MainConsoleController;

@Component
@Profile("!test")
public class ConsoleRunner {
    public static void main(String[] args) {
        MainConsoleController mainConsoleController = ControllerFactory.getInstance().getMainConsoleController();
        mainConsoleController.run();
    }
}
