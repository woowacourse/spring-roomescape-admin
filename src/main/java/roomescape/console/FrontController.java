package roomescape.console;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import roomescape.console.controller.ControllerAdapter;
import roomescape.console.utils.ControllerAdapterMapper;
import roomescape.console.view.ConsoleRequest;
import roomescape.console.view.ConsoleResponse;
import roomescape.console.view.InputView;
import roomescape.console.view.OutputView;

@Component
public class FrontController implements CommandLineRunner {
    private final InputView inputView;
    private final OutputView outputView;
    private final ControllerAdapterMapper controllerAdapterMapper;

    public FrontController(ControllerAdapterMapper controllerAdapterMapper) {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.controllerAdapterMapper = controllerAdapterMapper;
    }

    @Override
    public void run(String... args) {
        while (true) {
            outputView.printCommandGuide();
            try {
                ConsoleRequest request = inputView.getRequest();
                if (request.isEnded()) {
                    break;
                }
                ControllerAdapter handler = controllerAdapterMapper.getHandler(request);
                ConsoleResponse response = handler.dispatch(request);
                outputView.printResponse(response);
            } catch (RuntimeException e) {
                outputView.printErrorMessage(e);
            }
        }
    }
}
