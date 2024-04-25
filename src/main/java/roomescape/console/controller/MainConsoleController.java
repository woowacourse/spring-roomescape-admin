package roomescape.console.controller;

import org.springframework.stereotype.Component;
import roomescape.console.config.ControllerMapper;
import roomescape.console.view.AdminMenu;
import roomescape.console.view.InputView;
import roomescape.console.view.OutputView;

@Component
public class MainConsoleController {

    private final InputView inputView;
    private final ControllerMapper controllerMapper;

    public MainConsoleController(InputView inputView, ControllerMapper controllerMapper) {
        this.inputView = inputView;
        this.controllerMapper = controllerMapper;
    }

    public void run() {
        while (true) {
            try {
                AdminMenu adminMenu = inputView.inputAdminMenu();

                if (adminMenu == AdminMenu.QUIT) {
                    break;
                }

                ConsoleController consoleController = controllerMapper.getController(adminMenu);
                consoleController.run();
            } catch (Exception e) {
                OutputView.printError(e.getMessage());
            }
        }
    }
}
