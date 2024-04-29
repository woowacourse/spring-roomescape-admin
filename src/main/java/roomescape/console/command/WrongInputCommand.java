package roomescape.console.command;

import org.springframework.stereotype.Component;
import roomescape.console.ConsoleOutputView;

@Component
public class WrongInputCommand implements ConsoleCommand {

    private final ConsoleOutputView consoleOutputView;

    public WrongInputCommand(ConsoleOutputView consoleOutputView) {
        this.consoleOutputView = consoleOutputView;
    }

    @Override
    public void conduct() {
        consoleOutputView.printErrorMessage("잘못된 명령입니다.");
    }
}
