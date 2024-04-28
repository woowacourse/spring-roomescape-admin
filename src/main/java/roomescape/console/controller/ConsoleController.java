package roomescape.console.controller;

import roomescape.console.view.OutputView;

public class ConsoleController {
    private final OutputView outputView;

    public ConsoleController(OutputView outputView) {
        this.outputView = outputView;
    }

    public void run() {
        outputView.printStartMessage();
        outputView.printAdminFunction();
    }
}
