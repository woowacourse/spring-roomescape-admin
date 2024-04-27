package roomescape.console.utils;

import org.springframework.stereotype.Component;
import roomescape.console.controller.ConsoleController;
import roomescape.console.view.ConsoleRequest;

import java.util.Map;

@Component
public class ControllerMapper {
    private final Map<Command, ConsoleController> controllers;

    public ControllerMapper(Map<Command, ConsoleController> controllers) {
        this.controllers = controllers;
    }

    public ConsoleController getHandler(ConsoleRequest request) {
        Command command = Command.from(request.getCommand());
        return controllers.get(command);
    }
}
