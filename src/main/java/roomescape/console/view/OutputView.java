package roomescape.console.view;

import java.util.List;
import org.springframework.stereotype.Component;
import roomescape.dto.response.ReservationResponse;
import roomescape.dto.response.ReservationTimeResponse;

@Component
public class OutputView {
    public void printReservationInfo(List<ReservationResponse> reservationResponses,
                                     List<ReservationTimeResponse> reservationTimeResponses) {
        printReservations(reservationResponses);
        printReservationTimes(reservationTimeResponses);
    }

    private void printReservations(List<ReservationResponse> reservations) {
        System.out.println("전체 예약 현황");
        System.out.printf("%-15s %-15s %-15s %-10s%n", "예약번호", "예약자", "날짜", "시간");
        reservations.forEach(
                reservation -> {
                    System.out.printf("%-18d %-17s %-16s %s%n",
                            reservation.id(),
                            reservation.name(),
                            reservation.date(),
                            reservation.time().startAt());
                }
        );
        System.out.println();
    }

    private void printReservationTimes(List<ReservationTimeResponse> reservationTimes) {
        System.out.println("전체 예약 시간");
        System.out.printf("%-15s %-15s%n", "시간번호", "시간");
        reservationTimes.forEach(
                reservationTime -> {
                    System.out.printf("%-18d %s%n", reservationTime.id(), reservationTime.startAt());
                }
        );
    }
}
