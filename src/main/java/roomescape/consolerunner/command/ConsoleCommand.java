package roomescape.consolerunner.command;

import org.springframework.stereotype.Component;
import roomescape.controller.console.ReservationConsoleController;
import roomescape.controller.console.ReservationTimeConsoleController;
import roomescape.view.console.ConsoleInputView;
import roomescape.view.console.ConsoleOutputView;

@Component
public interface ConsoleCommand {

    void conduct(ReservationConsoleController reservationConsoleController,
                 ReservationTimeConsoleController reservationTimeConsoleController,
                 ConsoleInputView consoleInputView,
                 ConsoleOutputView consoleOutputView);
}
