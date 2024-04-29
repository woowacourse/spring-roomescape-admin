package roomescape.view;

import java.util.List;
import roomescape.dto.ReservationResponse;
import roomescape.dto.ReservationTimeResponse;

public class OutputView {
    public void printStartMessage() {
        System.out.println("[🚪 방탈출 관리 페이지]");
    }

    public void printNoReservation() {
        System.out.println("예약이 없습니다." + System.lineSeparator());
    }

    public void printNoReservationTime() {
        System.out.println("시간이 없습니다." + System.lineSeparator());
    }

    public void printReservations(List<ReservationResponse> reservations) {
        System.out.println("예약 번호 | 예약자 | 날짜 | 시간");
        reservations.forEach(this::printReservation);
        System.out.println();
    }

    private void printReservation(ReservationResponse reservation) {
        System.out.println(
                        reservation.id() + " | " +
                        reservation.name() + " | " +
                        reservation.date() + " | " +
                        reservation.time().startAt());
    }

    public void printAddReservationTitle() {
        System.out.println("[예약 추가]");
    }

    public void printReservationTimes(List<ReservationTimeResponse> reservationTimes) {
        System.out.println("시간 번호 | 시간");
        reservationTimes.forEach(this::printReservationTime);
        System.out.println();
    }

    private void printReservationTime(ReservationTimeResponse reservationTime) {
        System.out.println(reservationTime.id() + " | " + reservationTime.startAt());
    }

    public void printDeleteReservationTitle() {
        System.out.println("[예약 삭제]");
    }

    public void printDeleteReservationTimeTitle() {
        System.out.println("[예약 시간 삭제]");
    }
}
