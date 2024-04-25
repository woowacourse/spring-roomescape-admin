package roomescape.console.config;

import java.util.Map;
import roomescape.console.controller.ConsoleController;
import roomescape.console.view.AdminMenu;

public class ControllerMapper {

    private final Map<AdminMenu, ConsoleController> controllerByMenu;

    public ControllerMapper(Map<AdminMenu, ConsoleController> controllerByMenu) {
        this.controllerByMenu = controllerByMenu;
    }

    public ConsoleController getController(AdminMenu menu) {
        return controllerByMenu.get(menu);
    }
}
