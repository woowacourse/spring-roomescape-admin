package roomescape.console.controller;

import roomescape.console.view.ConsoleRequest;
import roomescape.console.view.ConsoleResponse;

public interface ControllerAdapter {
    ConsoleResponse dispatch(ConsoleRequest request);
}
