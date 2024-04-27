package roomescape.console.controller;

import roomescape.console.request.ConsoleRequest;
import roomescape.console.response.ConsoleResponse;

public interface ConsoleController {
    ConsoleResponse dispatch(ConsoleRequest request);
}
