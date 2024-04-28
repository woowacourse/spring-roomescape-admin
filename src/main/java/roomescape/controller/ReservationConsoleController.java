package roomescape.controller;

import java.util.List;
import roomescape.controller.request.ReservationRequest;
import roomescape.controller.request.ReservationTimeRequest;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;
import roomescape.view.InputView;
import roomescape.view.OutputView;

public class ReservationConsoleController {

    private final InputView inputView;
    private final OutputView outputView;
    private final ReservationService reservationService;
    private final ReservationTimeService reservationTimeService;

    public ReservationConsoleController(InputView inputView, OutputView outputView,
                                        ReservationService reservationService,
                                        ReservationTimeService reservationTimeService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.reservationService = reservationService;
        this.reservationTimeService = reservationTimeService;
    }

    public void start() {
        outputView.printCommands();
        int command;
        while (true) {
            command = inputView.askInputNumber();
            if (command == 1) { //방탈출 예약 조회
                printReservations();
            }
            if (command == 2) { //방탈출 예약시간 조회
                printReservationTimes();
            }
            if (command == 3) { //방탈출 예약 추가
                addReservations();
            }
            if (command == 4) { //방탈출 예약시간 추가
                addReservationTimes();
            }
            if (command == 5) { //방탈출 예약 삭제
                deleteReservation();
            }
            if (command == 6) { // 방탈출 예약시간 삭제
                deleteReservationTime();
            }
            if (command == 7) {
                outputView.printEnd();
                break;
            }
        }

    }

    public void printReservations() {
        List<Reservation> allReservations = reservationService.findAllReservations();
        outputView.printAllReservations(allReservations);
    }

    public void printReservationTimes() {
        List<ReservationTime> allReservationTimes = reservationTimeService.findAllReservationTimes();
        outputView.printAllReservationTimes(allReservationTimes);
    }


    public void addReservations() {
        List<ReservationTime> allReservationTimes = reservationTimeService.findAllReservationTimes();
        outputView.printAllReservationTimes(allReservationTimes);
        ReservationRequest request = inputView.askInputReservation();
        reservationService.addReservation(request);
        outputView.printReservationComplete();
    }

    public void addReservationTimes() {
        ReservationTimeRequest request = inputView.askInputReservationTime();
        reservationTimeService.addReservationTime(request);
        outputView.printAddReservationTime();
    }

    public void deleteReservation() {
        long reservationId = inputView.askInputReservationId();
        reservationService.deleteReservation(reservationId);
        outputView.printReservationDelete();
    }

    public void deleteReservationTime() {
        long reservationTimeId = inputView.askInputReservationTimeId();
        reservationTimeService.deleteReservationTime(reservationTimeId);
        outputView.printReservationTimeDelete();
    }
}
