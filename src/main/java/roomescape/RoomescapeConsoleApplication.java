package roomescape;

import java.util.List;
import roomescape.controller.ReservationController;
import roomescape.controller.ReservationTimeController;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.repository.MemoryReservationRepository;
import roomescape.repository.MemoryReservationTimeRepository;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;
import roomescape.view.InputMenu;
import roomescape.view.OutputView;
import roomescape.view.RoomescapeView;

public class RoomescapeConsoleApplication {

    public static void main(String[] args) {
        final RoomescapeView view = new RoomescapeView();
        ReservationRepository reservationRepository = new MemoryReservationRepository();
        ReservationTimeRepository reservationTimeRepository = new MemoryReservationTimeRepository();

        final ReservationController reservationController = new ReservationController(
            new ReservationService(
                reservationRepository,
                reservationTimeRepository
            ));
        final ReservationTimeController reservationTimeController = new ReservationTimeController(
            new ReservationTimeService(
                reservationTimeRepository
            ));

        new RoomescapeConsoleApplication(view, reservationController, reservationTimeController).run();
    }

    private final RoomescapeView view;
    private final ReservationController reservationController;
    private final ReservationTimeController reservationTimeController;

    public RoomescapeConsoleApplication(
        RoomescapeView view,
        ReservationController reservationController,
        ReservationTimeController reservationTimeController
    ) {
        this.view = view;
        this.reservationController = reservationController;
        this.reservationTimeController = reservationTimeController;
    }

    private void run() {
        InputMenu menu = InputMenu.none();
        while (!menu.isExit()) {
            try {
                menu = view.askMenu();
                doService(menu);
            } catch (RuntimeException e) {
                OutputView.printErrorMessage(e.getMessage());
            } catch (Exception e) {
                OutputView.printErrorMessage("알 수 서버 에러가 발생했습니다.");
            }
        }
    }

    private void doService(InputMenu menu) {
        switch (menu) {
            case TIMES_GET_ALL -> runGetAllReservationTimeController();
            case TIMES_ADD -> runAddReservationTimeController();
            case TIMES_DELETE -> runDeleteReservationTimeController();
            case RESERVATION_GET_ALL -> runGetAllReservationController();
            case RESERVATION_ADD -> runAddReservationController();
            case RESERVATION_DELETE -> runDeleteReservationController();
        }
    }

    private void runGetAllReservationTimeController() {
        List<ReservationTimeResponse> reservationTimes = reservationTimeController.getAllReservationTimes();
        view.printReservationTimes(reservationTimes);
    }

    private void runAddReservationTimeController() {
        ReservationTimeRequest request = view.askReservationTimeRequest();
        ReservationTimeResponse reservationTimeResponse = reservationTimeController.addReservationTime(request);
        view.printReservationTime(reservationTimeResponse);
    }

    private void runDeleteReservationTimeController() {
        long reservationTimeId = view.askReservationTimeId();
        reservationTimeController.deleteReservationTime(reservationTimeId);
    }

    private void runGetAllReservationController() {
        List<ReservationResponse> reservations = reservationController.getAllReservations();
        view.printReservations(reservations);
    }

    private void runAddReservationController() {
        ReservationRequest request = view.askReservationRequest();
        ReservationResponse reservationResponse = reservationController.addReservation(request);
        view.printReservation(reservationResponse);
    }

    private void runDeleteReservationController() {
        long reservationId = view.askReservationId();
        reservationController.deleteReservation(reservationId);
    }
}
