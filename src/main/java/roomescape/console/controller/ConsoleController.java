package roomescape.console.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import roomescape.console.constant.ConsoleFunction;
import roomescape.console.constant.ReservationFunction;
import roomescape.console.constant.ReservationTimeFunction;
import roomescape.console.view.InputView;
import roomescape.console.view.OutputView;
import roomescape.presentation.dto.CreateReservationDto;
import roomescape.presentation.dto.CreateReservationTimeDto;
import roomescape.presentation.dto.ReservationResponseDto;
import roomescape.presentation.dto.ReservationTimeResponseDto;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;

@Component
public class ConsoleController {

    private final InputView inputView;
    private final OutputView outputView;
    private final ReservationService reservationService;
    private final ReservationTimeService reservationTimeService;

    private boolean quitFlag = false;

    public ConsoleController(InputView inputView, OutputView outputView,
                             @Qualifier("consoleReservationService") ReservationService reservationService,
                             @Qualifier("consoleReservationTimeService") ReservationTimeService reservationTimeService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.reservationService = reservationService;
        this.reservationTimeService = reservationTimeService;
    }

    public void run() {
        outputView.printStartMessage();
        while (!quitFlag) {
            try {
                ConsoleFunction consoleFunction = ConsoleFunction.getSystemFunction(inputView.selectAdminFunction());
                processSystemFunction(consoleFunction);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
        outputView.printEndMessage();
    }

    private void processSystemFunction(ConsoleFunction consoleFunction) {
        if (consoleFunction == ConsoleFunction.RESERVATION) {
            ReservationFunction reservationFunction = ReservationFunction.getReservationFunction(
                    inputView.selectReservationFunction());
            processReservationFunction(reservationFunction);
        } else if (consoleFunction == ConsoleFunction.RESERVATION_TIME) {
            ReservationTimeFunction reservationTimeFunction = ReservationTimeFunction.getReservationTimeFunction(
                    inputView.selectReservationTimeFunction());
            processReservationTimeFunction(reservationTimeFunction);
        } else if (consoleFunction == ConsoleFunction.QUIT) {
            quitFlag = true;
        }
    }

    private void processReservationTimeFunction(ReservationTimeFunction function) {
        if (function == ReservationTimeFunction.ADD_RESERVATION_TIME) {
            createReservationTime();
        } else if (function == ReservationTimeFunction.GET_RESERVATION_TIMES) {
            getAllReservationTimes();
        } else if (function == ReservationTimeFunction.DELETE_RESERVATION_TIME) {
            deleteReservationTime();
        }
    }

    private void createReservationTime() {
        CreateReservationTimeDto createReservationTimeDto = inputView.inputCreateReservationTime();
        ReservationTimeResponseDto reservationTime = reservationTimeService.createReservationTime(
                createReservationTimeDto);
        outputView.printSuccessToCreateReservationTime(reservationTime);
    }

    private void getAllReservationTimes() {
        List<ReservationTimeResponseDto> reservationTimes = reservationTimeService.getAllReservationTimes();
        outputView.printReservationTimes(reservationTimes);
    }

    private void deleteReservationTime() {
        Long id = inputView.inputDeleteReservationTimeId();
        reservationTimeService.deleteReservationTime(id);
        outputView.printSuccessToDeleteReservationTime();
    }

    private void processReservationFunction(ReservationFunction function) {
        if (function == ReservationFunction.ADD_RESERVATION) {
            createReservation();
        } else if (function == ReservationFunction.GET_RESERVATIONS) {
            getReservations();
        } else if (function == ReservationFunction.DELETE_RESERVATION) {
            deleteReservation();
        }
    }

    private void createReservation() {
        CreateReservationDto createReservationDto = inputView.inputCreateReservation();
        ReservationResponseDto reservation = reservationService.createReservation(createReservationDto);
        outputView.printSuccessToCreateReservation(reservation);
    }

    private void getReservations() {
        List<ReservationResponseDto> reservations = reservationService.getAllReservations();
        outputView.printReservations(reservations);
    }

    private void deleteReservation() {
        Long id = inputView.inputDeleteReservationId();
        reservationService.deleteReservation(id);
        outputView.printSuccessToDeleteReservation();
    }

}
