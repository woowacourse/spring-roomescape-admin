package roomescape.console.controller;

import org.springframework.stereotype.Controller;
import roomescape.console.repository.ReservationMemoryRepository;
import roomescape.console.repository.ReservationTimeMemoryRepository;
import roomescape.console.view.InputView;
import roomescape.console.view.OutputView;
import roomescape.core.dto.ReservationRequest;
import roomescape.core.dto.ReservationResponse;
import roomescape.core.dto.ReservationTimeRequest;
import roomescape.core.dto.ReservationTimeResponse;
import roomescape.core.service.ReservationService;
import roomescape.core.service.ReservationTimeService;

@Controller
public class MainController {
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final ReservationService reservationService;
    private final ReservationTimeService reservationTimeService;

    public MainController() {
        this.reservationService = new ReservationService(new ReservationMemoryRepository());
        this.reservationTimeService = new ReservationTimeService(new ReservationTimeMemoryRepository());
    }

    public void run() {
        outputView.printReservations(reservationService.readReservations());
        outputView.printReservationTimes(reservationTimeService.readTimes());
        int selectedMenu = inputView.readSelectMenu();
        if (selectedMenu == 7) {
            return;
        }
        selectAction(selectedMenu);
        run();
    }

    private void selectAction(int selectedMenu) {
        if (selectedMenu == 1 || selectedMenu == 2) {
            selectReservation(selectedMenu);
            return;
        }
        selectReservationTime(selectedMenu);
    }

    private void selectReservation(int selectedMenu) {
        if (selectedMenu == 1) {
            selectReservationCreate();
            return;
        }
        selectReservationDelete();
    }

    private void selectReservationTime(int selectedMenu) {
        if (selectedMenu == 3) {
            selectReservationTimeCreate();
            return;
        }
        selectReservationTimeDelete();
    }

    private void selectReservationCreate() {
        ReservationRequest reservationRequest = inputView.readReservationCreate();
        ReservationResponse reservationResponse = reservationService.createReservation(reservationRequest);
        outputView.printReservationCreate(reservationResponse);
    }

    private void selectReservationDelete() {
        Long id = inputView.readReservationDelete();
        if (reservationService.deleteReservation(id)) {
            outputView.printReservationDelete();
        }
    }

    private void selectReservationTimeCreate() {
        ReservationTimeRequest reservationTimeRequest = inputView.readReservationTimeCreate();
        ReservationTimeResponse reservationTimeResponse = reservationTimeService.createTime(reservationTimeRequest);
        outputView.printReservationTimeCreate(reservationTimeResponse);
    }

    private void selectReservationTimeDelete() {
        Long id = inputView.readReservationTimeDelete();
        if (reservationTimeService.deleteTime(id)) {
            outputView.printReservationTimeDelete();
        }

    }
}
