package roomescape.console.controller;

import java.time.LocalTime;
import roomescape.console.view.View;
import roomescape.presentation.dto.ReservationTimeRequestDto;
import roomescape.presentation.dto.ReservationTimeResponseDto;
import roomescape.service.ReservationTimeService;

public final class ConsoleReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ConsoleReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    public void showReservationTimes() {
        View.printReservationTimes(reservationTimeService.getAllReservationTimes());
    }

    public void addReservationTime() {
        LocalTime time = View.readReservationTime();
        ReservationTimeResponseDto reservationTimeResponseDto = reservationTimeService.addReservationTime(
                new ReservationTimeRequestDto(time)
        );
        View.printReservationTime(reservationTimeResponseDto);
    }

    public void deleteReservationTime() {
        showReservationTimes();
        Long id = View.readDeleteReservationTimeId();
        reservationTimeService.deleteReservationTime(id);
        View.printDeleteReservation(id);
    }
}
