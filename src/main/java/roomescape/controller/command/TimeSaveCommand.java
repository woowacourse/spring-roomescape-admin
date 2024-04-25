package roomescape.controller.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.service.ReservationTimeService;
import roomescape.view.ConsoleView;

@Component
public class TimeSaveCommand extends TimeCommand {

    @Autowired
    public TimeSaveCommand(ConsoleView consoleView, ReservationTimeService reservationTimeService) {
        super(consoleView, reservationTimeService);
    }

    public void execute() {
        consoleView.printAllTimes(reservationTimeService.findTimes());
        ReservationTimeRequestDto timeRequestDto = consoleView.readTimeToAdd();
        reservationTimeService.addTime(timeRequestDto);
    }
}
