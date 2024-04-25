package roomescape.console.config;

import java.util.Map;
import org.springframework.stereotype.Component;
import roomescape.console.controller.ConsoleController;
import roomescape.console.view.AdminMenu;

@Component
public class ControllerMapper {

    private final Map<AdminMenu, ConsoleController> controllerByMenu;

    public ControllerMapper(Map<AdminMenu, ConsoleController> controllerByMenu) {
        this.controllerByMenu = controllerByMenu;
    }

    public ConsoleController getController(AdminMenu menu) {
        return controllerByMenu.get(menu);
    }
}
