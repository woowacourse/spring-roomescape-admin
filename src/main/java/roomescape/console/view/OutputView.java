package roomescape.console.view;

import java.util.List;
import org.springframework.stereotype.Component;
import roomescape.service.dto.ReservationServiceResponse;
import roomescape.service.dto.ReservationTimeServiceResponse;

@Component
public class OutputView {

    public void printReservations(List<ReservationServiceResponse> reservations) {
        reservations.forEach(System.out::println);
    }

    public void printCreateReservationSuccess(ReservationServiceResponse reservation) {
        System.out.println("예약 생성에 성공했습니다. " + reservation);
    }

    public void printDeleteReservationSuccess() {
        System.out.println("예약 삭제를 성공했습니다.");
    }

    public void printReservationTimes(List<ReservationTimeServiceResponse> reservationTimes) {
        System.out.println(reservationTimes);
    }

    public void printCreateReservationTimeSuccess(ReservationTimeServiceResponse reservationTimeServiceResponse) {
        System.out.println("예약 시간 생성에 성공했습니다. " + reservationTimeServiceResponse);
    }

    public void printDeleteReservationTimeSuccess() {
        System.out.println("예약 시간 삭제를 성공했습니다.");
    }

    public void printError(String errorMessage) {
        System.out.println(errorMessage);
    }
}
