package roomescape;

import roomescape.console.config.ControllerFactory;
import roomescape.console.controller.MainConsoleController;

public class ConsoleRunner {
    public static void main(String[] args) {
        MainConsoleController mainConsoleController = ControllerFactory.getInstance().getMainConsoleController();
        mainConsoleController.run();
    }
}
