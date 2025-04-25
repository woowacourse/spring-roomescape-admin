package roomescape.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import roomescape.constant.Function;
import roomescape.dto.CreateReservationDto;
import roomescape.dto.CreateReservationTimeDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.dto.ReservationTimeResponseDto;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;
import roomescape.view.InputView;
import roomescape.view.OutputView;

@Component
public class ConsoleController {

    private final InputView inputView;
    private final OutputView outputView;
    private final ReservationService reservationService;
    private final ReservationTimeService reservationTimeService;

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
        while (true) {
            try {
                Function function = Function.getSystemFunction(inputView.selectAdminFunction());
                processSystemFunction(function);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void processSystemFunction(Function function) {
        if (function == Function.RESERVATION) {
            Function reservationFunction = Function.getReservationFunction(
                    inputView.selectReservationFunction());
            processReservationFunction(reservationFunction);
        } else if (function == Function.RESERVATION_TIME) {
            Function reservationTimeFunction = Function.getReservationTimeFunction(
                    inputView.selectReservationTimeFunction());
            processReservationTimeFunction(reservationTimeFunction);
        }
    }

    private void processReservationTimeFunction(Function function) {
        if (function == Function.ADD_RESERVATION_TIME) {
            createReservationTime();
        } else if (function == Function.GET_RESERVATION_TIMES) {
            getAllReservationTimes();
        } else if (function == Function.DELETE_RESERVATION_TIME) {
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

    private void processReservationFunction(Function function) {
        if (function == Function.ADD_RESERVATION) {
            createReservation();
        } else if (function == Function.GET_RESERVATIONS) {
            getReservations();
        } else if (function == Function.DELETE_RESERVATION) {
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
