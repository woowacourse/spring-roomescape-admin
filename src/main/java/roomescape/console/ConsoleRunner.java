package roomescape.console;

import org.springframework.http.ResponseEntity;
import roomescape.common.exception.ApiException;
import roomescape.controller.ReservationController;
import roomescape.controller.ReservationTimeController;
import roomescape.controller.dto.request.ReservationRequest;
import roomescape.controller.dto.request.ReservationTimeRequest;
import roomescape.controller.dto.response.ReservationResponse;
import roomescape.controller.dto.response.ReservationTimeResponse;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.util.List;
import java.util.function.Supplier;

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
        while ((op = readUntilValid(ConsoleInputView::readOption)) != 7) {
            switch (op) {
                case 1 -> addTime();
                case 2 -> getTimeList();
                case 3 -> deleteReservationTime();
                case 4 -> addReservation();
                case 5 -> getReservationList();
                case 6 -> deleteReservation();
            }
        }
    }

    private void addTime() {
        ReservationTimeRequest request = readUntilValid(ConsoleInputView::readTimeRequest);
        ResponseEntity<ReservationTimeResponse> response =
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
        Long deleteId = readUntilValid(ConsoleInputView::readDeleteTimeId);
        controllerExceptionHandler(() -> reservationTimeController.deleteReservationTime(deleteId));
    }

    private void addReservation() {
        ReservationRequest request = readUntilValid(ConsoleInputView::readReservationRequest);
        ResponseEntity<ReservationResponse> response =
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
        Long deleteId = readUntilValid(ConsoleInputView::readDeleteReservationId);
        controllerExceptionHandler(() -> reservationController.deleteReservation(deleteId));
    }

    /**
     * "입력에 대한 예외를 처리하고 적절한 입력이 들어올 때까지 반복하는 메서드"
     *
     * @param readOperation: 특정 입력을 받는 작업
     * @return: 입력값
     */
    private <T> T readUntilValid(Supplier<T> readOperation) {
        while (true) {
            try {
                return readOperation.get();
            } catch (ConsoleException e) {
                ConsoleOutputView.printErrorMessage(e.getMessage());
            } catch (Exception e) {
                ConsoleOutputView.printErrorMessage("알 수 없는 에러가 발생했습니다. 잠시 후 다시 시도해주세요.");
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
        } catch (ApiException e) {
            ConsoleOutputView.printErrorMessage(e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).build();
        }
    }
}
