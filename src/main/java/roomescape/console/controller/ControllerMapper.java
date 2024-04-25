package roomescape.console.controller;

import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ControllerMapper {

    private final Map<Integer, ConsoleController> controllerByMenu;

    public ControllerMapper(Map<Integer, ConsoleController> controllerByMenu) {
        this.controllerByMenu = controllerByMenu;
    }

    public ConsoleController getController(int menu) {
        return controllerByMenu.get(menu);
    }
}
