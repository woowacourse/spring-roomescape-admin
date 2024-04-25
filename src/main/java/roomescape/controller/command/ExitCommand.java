package roomescape.controller.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import roomescape.view.ConsoleView;

@Component
public class ExitCommand extends Command {

    @Autowired
    public ExitCommand(ConsoleView consoleView) {
        super(consoleView);
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}
