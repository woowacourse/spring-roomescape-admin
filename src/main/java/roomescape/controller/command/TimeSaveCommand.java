package roomescape.controller.command;

import roomescape.dto.ReservationTimeRequestDto;
import roomescape.service.ReservationTimeService;
import roomescape.view.ConsoleView;

public class TimeSaveCommand extends TimeCommand {

    public TimeSaveCommand(ConsoleView consoleView, ReservationTimeService reservationTimeService) {
        super(consoleView, reservationTimeService);
    }

    public void execute() {
        ReservationTimeRequestDto timeRequestDto = consoleView.readTimeToAdd();
        reservationTimeService.addTime(timeRequestDto);
    }
}
