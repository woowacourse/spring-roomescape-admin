package roomescape.console.controller;

import roomescape.console.view.ConsoleCommand;
import roomescape.console.view.InputView;
import roomescape.console.view.OutputView;

public class ConsoleRunner {

    private final DispatcherConsole dispatcherConsole;
    private final InputView inputView;
    private final OutputView outputView;

    public ConsoleRunner(DispatcherConsole dispatcherConsole, InputView inputView, OutputView outputView) {
        this.dispatcherConsole = dispatcherConsole;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        while (true) {
            handleException();
        }
    }

    private void handleException() {
        try {
            ConsoleCommand consoleCommand = inputView.readCommand();
            dispatcherConsole.doDispatch(consoleCommand);
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
        } catch (Exception e) {
            outputView.printError(e.getMessage());
        }
    }
}
