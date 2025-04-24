package roomescape.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import roomescape.constant.Function;
import roomescape.dto.CreateReservationDto;
import roomescape.dto.CreateReservationTimeDto;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;
import roomescape.exception.InvalidFunctionException;
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
            } catch (InvalidFunctionException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void processSystemFunction(Function function) {
        if (function == Function.RESERVATION_TIME) {
            Function reservationTimeFunction = Function.getReservationTimeFunction(
                    inputView.selectReservationTimeFunction());
            processReservationTimeFunction(reservationTimeFunction);
        } else if (function == Function.RESERVATION) {
            Function reservationFunction = Function.getReservationFunction(
                    inputView.selectReservationFunction());
            processReservationFunction(reservationFunction);
        }
    }

    private void processReservationTimeFunction(Function function) {
        if (function == Function.ADD_RESERVATION_TIME) {
            CreateReservationTimeDto createReservationTimeDto = inputView.inputCreateReservationTime();
            ReservationTime reservationTime = reservationTimeService.createReservationTime(createReservationTimeDto);
            outputView.printSuccessToCreateReservationTime(reservationTime);
        } else if (function == Function.GET_RESERVATION_TIMES) {
            List<ReservationTime> reservationTimes = reservationTimeService.getAllReservationTimes();
            outputView.printReservationTimes(reservationTimes);
        } else if (function == Function.DELETE_RESERVATION_TIME) {
            Long id = inputView.inputDeleteReservationTimeId();
            reservationTimeService.deleteReservationTime(id);
            outputView.printSuccessToDeleteReservationTime();
        }
    }

    private void processReservationFunction(Function function) {
        if (function == Function.ADD_RESERVATION) {
            CreateReservationDto createReservationDto = inputView.inputCreateReservation();
            Reservation reservation = reservationService.createReservation(createReservationDto);
            outputView.printSuccessToCreateReservation(reservation);
        } else if (function == Function.GET_RESERVATIONS) {
            List<Reservation> reservations = reservationService.getAllReservations();
            outputView.printReservations(reservations);
        } else if (function == Function.DELETE_RESERVATION) {
            Long id = inputView.inputDeleteReservationId();
            reservationService.deleteReservation(id);
            outputView.printSuccessToDeleteReservation();
        }
    }

}
