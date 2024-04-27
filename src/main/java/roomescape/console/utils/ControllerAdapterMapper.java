package roomescape.console.utils;

import roomescape.console.controller.ControllerAdapter;
import roomescape.console.view.ConsoleRequest;

import java.util.Map;

public class ControllerAdapterMapper {
    private final Map<Command, ControllerAdapter> controllers;

    public ControllerAdapterMapper(Map<Command, ControllerAdapter> controllers) {
        this.controllers = controllers;
    }

    public ControllerAdapter getHandler(ConsoleRequest request) {
        Command command = Command.from(request.getCommand());
        return controllers.get(command);
    }
}
