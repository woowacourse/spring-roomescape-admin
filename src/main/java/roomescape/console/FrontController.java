package roomescape.console;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import roomescape.console.controller.ConsoleController;
import roomescape.console.request.ConsoleRequest;
import roomescape.console.response.ConsoleResponse;
import roomescape.console.utils.ControllerMapper;
import roomescape.console.view.InputView;
import roomescape.console.view.OutputView;

@Component
public class FrontController implements CommandLineRunner {
    private final InputView inputView;
    private final OutputView outputView;
    private final ControllerMapper controllerMapper;

    public FrontController(ControllerMapper controllerMapper) {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.controllerMapper = controllerMapper;
    }

    @Override
    public void run(String... args) {
        while (true) {
            try {
                ConsoleRequest request = inputView.getRequest();
                if (request.isEnded()) {
                    break;
                }
                ConsoleController handler = controllerMapper.getHandler(request);
                ConsoleResponse response = handler.dispatch(request);
                outputView.printResponse(response);
            } catch (RuntimeException e) {
                outputView.printErrorMessage(e);
            }
        }
    }
}
