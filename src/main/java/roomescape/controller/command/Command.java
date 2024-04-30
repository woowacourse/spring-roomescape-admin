package roomescape.controller.command;

import roomescape.view.ConsoleView;

public abstract class Command {

    protected final ConsoleView consoleView;

    public Command(ConsoleView consoleView) {
        this.consoleView = consoleView;
    }

    public abstract void execute();
}
