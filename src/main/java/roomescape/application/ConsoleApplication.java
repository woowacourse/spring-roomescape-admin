package roomescape.application;

import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import roomescape.dto.CreateReservationDto;
import roomescape.dto.CreateReservationTimeDto;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;
import roomescape.view.InputView;
import roomescape.view.OutputView;

@Component
public class ConsoleApplication implements CommandLineRunner {

    private final InputView inputView;
    private final OutputView outputView;
    private final ReservationService reservationService;
    private final ReservationTimeService reservationTimeService;

    public ConsoleApplication(InputView inputView, OutputView outputView,
                              @Qualifier("consoleReservationService") ReservationService reservationService,
                              @Qualifier("consoleReservationTimeService") ReservationTimeService reservationTimeService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.reservationService = reservationService;
        this.reservationTimeService = reservationTimeService;
    }

    @Override
    public void run(String... args) throws Exception {
        outputView.printStartMessage();
        while (true) {
            int function = inputView.selectAdminFunction();
            if (function == 1) {
                int reservationTimeFunction = inputView.selectReservationTimeFunction();
                processReservationTimeFunction(reservationTimeFunction);
            } else if (function == 2) {
                int reservationFunction = inputView.selectReservationFunction();
                processReservationFunction(reservationFunction);
            } else {
                System.out.println("잘못된 기능 입력입니다.");
                System.out.println();
            }
        }
    }

    private void processReservationTimeFunction(int function) {
        if (function == 1) {
            CreateReservationTimeDto createReservationTimeDto = inputView.inputCreateReservationTime();
            ReservationTime reservationTime = reservationTimeService.createReservationTime(createReservationTimeDto);
            outputView.printSuccessToCreateReservationTime(reservationTime);
        } else if (function == 2) {
            List<ReservationTime> reservationTimes = reservationTimeService.getAllReservationTimes();
            outputView.printReservationTimes(reservationTimes);
        } else if (function == 3) {
            Long id = inputView.inputDeleteReservationTimeId();
            reservationTimeService.deleteReservationTime(id);
            outputView.printSuccessToDeleteReservationTime();
        }
    }

    private void processReservationFunction(int function) {
        if (function == 1) {
            CreateReservationDto createReservationDto = inputView.inputCreateReservation();
            Reservation reservation = reservationService.createReservation(createReservationDto);
            outputView.printSuccessToCreateReservation(reservation);
        } else if (function == 2) {
            List<Reservation> reservations = reservationService.getAllReservations();
            outputView.printReservations(reservations);
        }
    }
}
