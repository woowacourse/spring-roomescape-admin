package roomescape.console.controller;

import java.util.List;
import roomescape.console.view.View;
import roomescape.presentation.dto.ReservationRequestDto;
import roomescape.presentation.dto.ReservationResponseDto;
import roomescape.presentation.dto.ReservationTimeResponseDto;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;

public final class ConsoleReservationController {

    private final ReservationService reservationService;
    private final ReservationTimeService reservationTimeService;

    public ConsoleReservationController(ReservationService reservationService,
                                        ReservationTimeService reservationTimeService) {
        this.reservationService = reservationService;
        this.reservationTimeService = reservationTimeService;
    }

    public void displayReservations() {
        View.printReservations(reservationService.getAllReservations());
    }

    public void reserve() {
        List<ReservationTimeResponseDto> allReservationTimes = reservationTimeService.getAllReservationTimes();
        ReservationRequestDto reservationRequestDto = View.readReservation(allReservationTimes);
        ReservationResponseDto reservationResponseDto = reservationService.makeReservation(reservationRequestDto);
        View.printReservation(reservationResponseDto);
    }

    public void cancel() {
        View.printReservations(reservationService.getAllReservations());
        Long id = View.readCancelReservationId();
        reservationService.cancelReservation(id);
        System.out.println("예약이 취소되었습니다.");
    }
}
