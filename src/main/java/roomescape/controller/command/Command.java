package roomescape.controller.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import roomescape.view.ConsoleView;

@Component
public abstract class Command {

    protected final ConsoleView consoleView;

    @Autowired
    public Command(ConsoleView consoleView) {
        this.consoleView = consoleView;
    }

    public abstract void execute();
}
