package roomescape.console;

import org.springframework.boot.CommandLineRunner;
import roomescape.console.controller.DispatcherConsole;
import roomescape.console.view.ConsoleCommand;
import roomescape.console.view.InputView;
import roomescape.console.view.OutputView;

public class ConsoleRunner implements CommandLineRunner {

    private final DispatcherConsole dispatcherConsole;
    private final InputView inputView;
    private final OutputView outputView;

    public ConsoleRunner(DispatcherConsole dispatcherConsole, InputView inputView, OutputView outputView) {
        this.dispatcherConsole = dispatcherConsole;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    @Override
    public void run(String... args) {
        Thread thread = new Thread(this::runConsoleReservation);
        thread.setDaemon(false);
        thread.start();
    }

    private void runConsoleReservation() {
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
            outputView.printError("서버 오류입니다.");
        }
    }
}
