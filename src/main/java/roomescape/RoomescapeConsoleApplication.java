package roomescape;

import roomescape.console.config.DependencyInjector;
import roomescape.console.presentation.DispatcherConsole;
import roomescape.console.view.Command;
import roomescape.console.view.InputView;
import roomescape.console.view.OutputView;

public class RoomescapeConsoleApplication {
    public static void main(String[] args) {
        DependencyInjector dependencyInjector = new DependencyInjector();
        DispatcherConsole dispatcherConsole = dependencyInjector.getDispatcherConsole();
        OutputView outputView = dependencyInjector.getOutputView();
        InputView inputView = dependencyInjector.getInputView();
        
        Command command;
        do {
            outputView.printCommand();
            command = inputView.askCommand();
            dispatcherConsole.doDispatch(command);
        } while (command != Command.END);
    }
}
