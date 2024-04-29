package roomescape.console.view;

import java.time.format.DateTimeFormatter;
import java.util.List;
import roomescape.domain.dto.ReservationResponse;
import roomescape.domain.dto.ReservationTimeResponse;

public class ConsoleOutputView {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public void printReservations(List<ReservationResponse> reservations) {
        System.out.println();
        System.out.println("※ 예약 현황 ※");
        reservations.forEach(this::printReservation);
    }

    public void printReservation(ReservationResponse reservation) {
        System.out.printf(
                "id : %3d, name : %6s, date : %s, start_at : %s%n",
                reservation.id(),
                reservation.name(),
                reservation.date().format(DATE_FORMATTER),
                reservation.time().startAt().format(TIME_FORMATTER));
    }

    public void printReservationTimes(List<ReservationTimeResponse> reservationTimes) {
        System.out.println();
        System.out.println("※ 예약 시간 현황 ※");
        reservationTimes.forEach(this::printReservationTime);
    }

    public void printReservationTime(ReservationTimeResponse reservationTime) {
        System.out.printf("id : %3d, start_at : %s%n", reservationTime.id(), reservationTime.startAt());
    }

}
