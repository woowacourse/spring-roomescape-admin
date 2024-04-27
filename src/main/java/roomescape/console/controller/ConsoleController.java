package roomescape.console.controller;

import roomescape.console.view.ConsoleRequest;
import roomescape.console.view.ConsoleResponse;

public interface ConsoleController {
    ConsoleResponse dispatch(ConsoleRequest request);
}
