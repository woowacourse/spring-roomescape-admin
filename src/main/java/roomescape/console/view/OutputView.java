package roomescape.console.view;

import org.springframework.stereotype.Component;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.util.List;

@Component
public class OutputView {

    public void printAllReservations(final List<Reservation> reservations) {
        final StringBuilder sb = new StringBuilder();
        for (Reservation reservation : reservations) {
            insertReservation(reservation, sb);
        }
        System.out.print(sb);
    }

    public void printSavedReservation(final Reservation reservation) {
        final StringBuilder sb = new StringBuilder();
        insertReservation(reservation, sb);
        System.out.print(sb);
    }

    public void printDeleteCompleteMessage() {
        System.out.println("삭제 완료되었습니다.");
    }

    private static void insertReservation(final Reservation reservation, final StringBuilder sb) {
        sb
                .append("[").append("\n")
                .append("id : ").append(reservation.id()).append("\n")
                .append("예약자 : ").append(reservation.name()).append("\n")
                .append("날짜 : ").append(reservation.date()).append("\n")
                .append("시작 시간 : ").append(reservation.startTime()).append("\n")
                .append("]").append("\n");
    }

    public void printAllReservationTimes(final List<ReservationTime> reservationTimes) {
        final StringBuilder sb = new StringBuilder();
        for (ReservationTime reservationTime : reservationTimes) {
            insertReservationTime(reservationTime, sb);
        }
        System.out.print(sb);
    }

    public void printSavedReservationTime(final ReservationTime reservationTime) {
        final StringBuilder sb = new StringBuilder();
        insertReservationTime(reservationTime, sb);
        System.out.print(sb);
    }

    private static void insertReservationTime(final ReservationTime reservationTime, final StringBuilder sb) {
        sb
                .append("[").append("\n")
                .append("id : ").append(reservationTime.id()).append("\n")
                .append("시작 시간 : ").append(reservationTime.startTime()).append("\n")
                .append("]").append("\n");
    }
}
