package roomescape.console;

import org.springframework.stereotype.Component;
import roomescape.console.controller.DispatcherConsole;
import roomescape.console.view.ConsoleCommand;
import roomescape.console.view.InputView;
import roomescape.console.view.OutputView;

@Component
public class ConsoleApplication {

    private final DispatcherConsole dispatcherConsole;
    private final InputView inputView;
    private final OutputView outputView;

    public ConsoleApplication(DispatcherConsole dispatcherConsole, InputView inputView, OutputView outputView) {
        this.dispatcherConsole = dispatcherConsole;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        while (true) {
            try {
                ConsoleCommand consoleCommand = inputView.readCommand();
                dispatcherConsole.doDispatch(consoleCommand);
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }
}
