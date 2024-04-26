package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import roomescape.controller.command.Command;
import roomescape.view.ConsoleView;
import roomescape.view.InputCommandMapper;

@Profile("console")
@Component
public class ConsoleController implements CommandLineRunner {

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

    @Override
    public void run(String... args) throws Exception {
        run();
    }
}
