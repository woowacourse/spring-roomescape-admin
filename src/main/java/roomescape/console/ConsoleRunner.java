package roomescape.console;

import org.springframework.http.ResponseEntity;
import roomescape.controller.ReservationController;
import roomescape.controller.ReservationTimeController;
import roomescape.controller.dto.request.ReservationRequest;
import roomescape.controller.dto.request.ReservationTimeRequest;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.util.List;
import java.util.function.Supplier;

import static roomescape.console.ConsoleInputView.*;
import static roomescape.console.ConsoleOutputView.*;

public class ConsoleRunner {

    private final ReservationController reservationController;
    private final ReservationTimeController reservationTimeController;

    public ConsoleRunner(ReservationController reservationController, ReservationTimeController reservationTimeController) {
        this.reservationController = reservationController;
        this.reservationTimeController = reservationTimeController;
    }

    public void run() {

        int op;
        while ((op = readOption()) != 7) {
            switch (op) {
                case 1:
                    addTime();
                    break;
                case 2:
                    getTimeList();
                    break;
                case 3:
                    deleteReservationTime();
                    break;
                case 4:
                    addReservation();
                    break;
                case 5:
                    getReservationList();
                    break;
                case 6:
                    deleteReservation();
                    break;
                default:
                    break;
            }
        }
    }

    private void addTime() {
        ReservationTimeRequest request = readOperationExceptionHandler(ConsoleInputView::readTimeRequest);
        ResponseEntity<ReservationTime> response =
                controllerExceptionHandler(() -> reservationTimeController.addReservationTime(request));

        if(response.getStatusCode().is2xxSuccessful()) {
            printTime(response.getBody());
        }
    }

    private void getTimeList() {
        ResponseEntity<List<ReservationTime>> response =
                controllerExceptionHandler(reservationTimeController::getReservationTimes);

        if(response.getStatusCode().is2xxSuccessful()) {
            printTimeList(response.getBody());
        }
    }

    private void deleteReservationTime() {
        Long deleteId = readOperationExceptionHandler(ConsoleInputView::readDeleteTimeId);
        controllerExceptionHandler(() -> reservationTimeController.deleteReservationTime(deleteId));
    }

    private void addReservation() {
        ReservationRequest request = readOperationExceptionHandler(ConsoleInputView::readReservationRequest);
        ResponseEntity<Reservation> response =
                controllerExceptionHandler(() -> reservationController.addReservation(request));

        if(response.getStatusCode().is2xxSuccessful()) {
            printReservation(response.getBody());
        }
    }

    private void getReservationList() {
        ResponseEntity<List<Reservation>> response =
                controllerExceptionHandler(reservationController::getReservations);

        if(response.getStatusCode().is2xxSuccessful()) {
            printReservationList(response.getBody());
        }
    }

    private void deleteReservation() {
        Long deleteId = readOperationExceptionHandler(ConsoleInputView::readDeleteReservationId);
        controllerExceptionHandler(() -> reservationController.deleteReservation(deleteId));
    }

    /**
     * "입력에 대한 예외를 처리하고 적절한 입력이 들어올 때까지 반복하는 메서드"
     *
     * @param readOperation: 특정 입력을 받는 작업
     * @return: 입력값
     */
    private <T> T readOperationExceptionHandler(Supplier<T> readOperation) {
        while (true) {
            try {
                return readOperation.get();
            } catch (RuntimeException e) {
                ConsoleOutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    /**
     * 특정 컨트롤러 작업의 예외를 처리하는 핸들러
     *
     * @param controllerOperation: 특정 입력을 받는 작업
     * @return: 입력값
     */
    private <T> ResponseEntity<T> controllerExceptionHandler(Supplier<ResponseEntity<T>> controllerOperation) {
        try {
            return controllerOperation.get();
        } catch (RuntimeException e) {
            ConsoleOutputView.printErrorMessage(e.getMessage());
            return ResponseEntity.badRequest().build(); // TODO: 예외에 따른 에러 코드 정의
        }
    }
}
