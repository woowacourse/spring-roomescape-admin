package roomescape.controller;

import static roomescape.controller.ReservationMenu.ADD_RESERVATION;
import static roomescape.controller.ReservationMenu.ADD_RESERVATION_TIME;
import static roomescape.controller.ReservationMenu.DELETE_RESERVATION;
import static roomescape.controller.ReservationMenu.DELETE_RESERVATION_TIME;

import java.time.LocalTime;
import java.util.List;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.repository.MemoryReservationRepository;
import roomescape.repository.MemoryReservationTimeRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;
import roomescape.view.InputView;
import roomescape.view.OutputView;

public class ConsoleReservationController {
    private static final InputView INPUT_VIEW = new InputView();
    private static final OutputView OUTPUT_VIEW = new OutputView();

    private final ReservationService reservationService;
    private final ReservationTimeService reservationTimeService;

    public ConsoleReservationController() {
        ReservationTimeRepository reservationTimeRepository = new MemoryReservationTimeRepository();
        this.reservationService = new ReservationService(new MemoryReservationRepository(), reservationTimeRepository);
        this.reservationTimeService = new ReservationTimeService(reservationTimeRepository);

    }

    public void run() {
        while (true) {
            showMainPage();
            selectMenu();
        }
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
            return;
        }
        OUTPUT_VIEW.printReservations(reservations);
    }

    private void showReservationTimeStatus() {
        List<ReservationTimeResponse> reservationTimes = reservationTimeService.findReservationTimes();
        if (reservationTimes.isEmpty()) {
            OUTPUT_VIEW.printNoReservationTime();
            return;
        }
        OUTPUT_VIEW.printReservationTimes(reservationTimes);
    }

    private void selectMenu() {
        int selectedMenu = INPUT_VIEW.readSelectedMenu();
        changePage(ReservationMenu.findReservationMenu(selectedMenu));
    }

    private void changePage(ReservationMenu selectedMenu) {
        if (selectedMenu == ADD_RESERVATION) {
            addReservation();
        }
        if (selectedMenu == DELETE_RESERVATION) {
            deleteReservation();
        }
        if (selectedMenu == ADD_RESERVATION_TIME) {
            addReservationTime();
        }
        if (selectedMenu == DELETE_RESERVATION_TIME) {
            deleteReservationTime();
        }
    }

    private void addReservation() {
        OUTPUT_VIEW.printAddReservationTitle();
        showReservationTimeStatus();
        if (checkReservationTimeEmpty()) {
            return;
        }
        ReservationRequest reservationRequest = INPUT_VIEW.readReservationRequest();
        reservationService.createReservation(reservationRequest);
    }

    private void deleteReservation() {
        OUTPUT_VIEW.printDeleteReservationTitle();
        showReservationStatus();
        if (reservationService.findReservations().isEmpty()) {
            return;
        }
        long selectedId = INPUT_VIEW.readDeleteReservationId();
        reservationService.deleteReservation(selectedId);
    }

    private void addReservationTime() {
        LocalTime input = INPUT_VIEW.readReservationTime();
        reservationTimeService.createReservationTime(new ReservationTimeRequest(input));
    }

    private void deleteReservationTime() {
        OUTPUT_VIEW.printDeleteReservationTimeTitle();
        showReservationTimeStatus();
        if (checkReservationTimeEmpty()) {
            return;
        }
        long selectedId = INPUT_VIEW.readDeleteReservationTimeId();
        reservationTimeService.deleteReservationTime(selectedId);
    }

    private boolean checkReservationTimeEmpty() {
        return reservationTimeService.findReservationTimes().isEmpty();
    }
}
