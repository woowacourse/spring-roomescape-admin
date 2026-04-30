package roomescape;

import static roomescape.view.InputMenu.RESERVATION_ADD;
import static roomescape.view.InputMenu.RESERVATION_DELETE;
import static roomescape.view.InputMenu.RESERVATION_GET_ALL;
import static roomescape.view.InputMenu.TIMES_ADD;
import static roomescape.view.InputMenu.TIMES_DELETE;
import static roomescape.view.InputMenu.TIMES_GET_ALL;

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
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void doService(InputMenu menu) {
        if (menu == TIMES_GET_ALL) {
            List<ReservationTimeResponse> reservationTimes = reservationTimeController.getAllReservationTimes();
            view.printReservationTimes(reservationTimes);

        } else if (menu == TIMES_ADD) {
            ReservationTimeRequest request = view.askReservationTimeRequest();
            ReservationTimeResponse reservationTimeResponse = reservationTimeController.addReservationTime(request);
            view.printReservationTime(reservationTimeResponse);

        } else if (menu == TIMES_DELETE) {
            long reservationTimeId = view.askReservationTimeId();
            reservationTimeController.deleteReservationTime(reservationTimeId);

        } else if (menu == RESERVATION_GET_ALL) {
            List<ReservationResponse> reservations = reservationController.getAllReservations();
            view.printReservations(reservations);

        } else if (menu == RESERVATION_ADD) {
            ReservationRequest request = view.askReservationRequest();
            ReservationResponse reservationResponse = reservationController.addReservation(request);
            view.printReservation(reservationResponse);

        } else if (menu == RESERVATION_DELETE) {
            long reservationId = view.askReservationId();
            reservationController.deleteReservation(reservationId);
        }
    }
}
