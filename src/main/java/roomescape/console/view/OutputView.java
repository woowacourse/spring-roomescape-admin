package roomescape.console.view;

import java.util.List;
import roomescape.core.service.dto.ReservationServiceResponse;
import roomescape.core.service.dto.ReservationTimeServiceResponse;

public class OutputView {

    public void printReservations(List<ReservationServiceResponse> reservations) {
        System.out.println("*********** 예약 목록 ***********\nID : 이름  날짜  시간");
        reservations.forEach(this::printReservation);
    }

    public void printReservation(ReservationServiceResponse reservation) {
        System.out.printf("%d : %s %s %s%n",
                reservation.id(), reservation.name(), reservation.date(), reservation.time().startAt());
    }

    public void printCreateReservationSuccess(ReservationServiceResponse reservation) {
        System.out.println("***** 예약 생성에 성공했습니다. *****\nID : 이름  날짜  시간");
        printReservation(reservation);
    }

    public void printDeleteReservationSuccess() {
        System.out.println("**** 예약 삭제를 성공했습니다. ****");
    }

    public void printReservationTimes(List<ReservationTimeServiceResponse> reservationTimes) {
        System.out.println("********* 예약 시간 목록 *********\nID : 시간");
        reservationTimes.forEach(this::printReservationTime);
    }

    private void printReservationTime(ReservationTimeServiceResponse reservationTimeServiceResponse) {
        System.out.printf("%d : %s%n", reservationTimeServiceResponse.id(), reservationTimeServiceResponse.startAt());
    }

    public void printCreateReservationTimeSuccess(ReservationTimeServiceResponse reservationTimeServiceResponse) {
        System.out.println("**** 예약 시간 생성에 성공했습니다. ****");
        printReservationTime(reservationTimeServiceResponse);
    }

    public void printDeleteReservationTimeSuccess() {
        System.out.println("**** 예약 시간 삭제를 성공했습니다. ****");
    }

    public void printError(String errorMessage) {
        System.out.println("****** ERROR ******\n" + errorMessage);
    }
}
