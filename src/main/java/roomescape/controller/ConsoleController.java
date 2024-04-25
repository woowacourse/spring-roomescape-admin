package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import roomescape.controller.command.Command;
import roomescape.view.ConsoleView;
import roomescape.view.InputCommandMapper;

@Controller
public class ConsoleController {

    private final ConsoleView consoleView;
    private final InputCommandMapper inputCommandMapper;

    @Autowired
    public ConsoleController(ConsoleView consoleView, InputCommandMapper inputCommandMapper) {
        this.consoleView = consoleView;
        this.inputCommandMapper = inputCommandMapper;
    }

    public void run() {
        while (true) {
            String rawCommand = consoleView.readCommand();
            Command command = inputCommandMapper.findCommand(rawCommand);
            command.execute();
        }
    }
}
