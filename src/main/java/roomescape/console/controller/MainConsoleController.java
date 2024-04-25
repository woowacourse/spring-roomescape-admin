package roomescape.console.controller;

import org.springframework.stereotype.Controller;
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
                int menu = inputView.inputMenu();
                ConsoleController consoleController = controllerMapper.getController(menu);
                consoleController.run();
            } catch (Exception e) {
                OutputView.printError(e.getMessage());
            }
        }
    }

}
