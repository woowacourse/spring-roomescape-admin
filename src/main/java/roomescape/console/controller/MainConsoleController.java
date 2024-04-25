package roomescape.console.controller;

import org.springframework.stereotype.Controller;
import roomescape.console.config.ControllerMapper;
import roomescape.console.view.AdminMenu;
import roomescape.console.view.InputView;
import roomescape.console.view.OutputView;

@Controller
public class MainConsoleController {

    private final InputView inputView;
    private final OutputView outputView;
    private final ControllerMapper controllerMapper;

    public MainConsoleController(InputView inputView, OutputView outputView, ControllerMapper controllerMapper) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.controllerMapper = controllerMapper;
    }

    public void run() {
        while (true) {
            try {
                outputView.outputAdminMenu();
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
