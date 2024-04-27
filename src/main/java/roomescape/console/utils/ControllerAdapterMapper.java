package roomescape.console.utils;

import roomescape.console.controller.ControllerAdapter;
import roomescape.console.view.ConsoleRequest;

import java.util.Map;

public class ControllerAdapterMapper {
    private final Map<ConsoleCommand, ControllerAdapter> controllers;

    public ControllerAdapterMapper(Map<ConsoleCommand, ControllerAdapter> controllers) {
        this.controllers = controllers;
    }

    public ControllerAdapter getHandler(ConsoleRequest request) {
        ConsoleCommand consoleCommand = ConsoleCommand.from(request.getCommand());
        return controllers.get(consoleCommand);
    }
}
