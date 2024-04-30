package roomescape.controller.command;

import roomescape.view.ConsoleView;

public class ExitCommand extends Command {

    public ExitCommand(ConsoleView consoleView) {
        super(consoleView);
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}
