package roomescape.controller;

import java.util.List;
import roomescape.dto.ReservationResponse;
import roomescape.dto.ReservationTimeResponse;
import roomescape.repository.MemoryReservationRepository;
import roomescape.repository.MemoryReservationTimeRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;
import roomescape.view.OutputView;

public class ConsoleReservationController {
    private static final OutputView OUTPUT_VIEW = new OutputView();

    private final ReservationService reservationService;
    private final ReservationTimeService reservationTimeService;

    public ConsoleReservationController() {
        ReservationTimeRepository reservationTimeRepository = new MemoryReservationTimeRepository();
        this.reservationService = new ReservationService(new MemoryReservationRepository(), reservationTimeRepository);
        this.reservationTimeService = new ReservationTimeService(reservationTimeRepository);

    }

    public void run() {
        showMainPage();
    }

    private void showMainPage() {
        OUTPUT_VIEW.printStartMessage();
        showReservationStatus();
        showReservationTimeStatus();
    }

    private void showReservationStatus() {
        List<ReservationResponse> reservations = reservationService.findReservations();
        if (reservations.isEmpty()) {
            OUTPUT_VIEW.printNoReservation();
        }
    }

    private void showReservationTimeStatus() {
        List<ReservationTimeResponse> reservationTimes = reservationTimeService.findReservationTimes();
        if (reservationTimes.isEmpty()) {
            OUTPUT_VIEW.printNoReservationTime();
        }
    }
}
