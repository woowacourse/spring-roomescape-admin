package roomescape;

import roomescape.console.controller.ConsoleController;
import roomescape.console.view.OutputView;

public class ConsoleApplication {
    public static void main(String[] args) {
        ConsoleController consoleController = new ConsoleController(new OutputView());
        consoleController.run();
    }
}
