package roomescape.view;

import java.util.List;
import roomescape.dto.ReservationResponse;
import roomescape.dto.ReservationTimeResponse;

public class OutputView {

    private static final String SEPARATOR = "-".repeat(30);

    public static void printErrorMessage(String message) {
        System.out.println();
        System.out.println("[ERROR] " + message);
    }

    public void askMenu() {
        System.out.println();
        System.out.println(SEPARATOR);
        System.out.println("메뉴 번호를 입력해주세요.");
    }

    public void printMenu() {
        System.out.println(InputMenu.getDisplayMenus());
    }

    public void askReservation() {
        System.out.println();
        System.out.println(SEPARATOR);
        System.out.println("예약 정보를 입력해주세요. (이름, yyyy-MM-dd, 시간ID)");
        System.out.println("예) 코로구, 2026-04-30, 1");
    }

    public void askReservationTime() {
        System.out.println();
        System.out.println(SEPARATOR);
        System.out.println("예약 시간을 입력해주세요. (HH:mm)");
        System.out.println("예) 09:30");
    }

    public void askReservationTimeId() {
        System.out.println();
        System.out.println(SEPARATOR);
        System.out.println("예약 시간 아이디를 입력해주세요.");
    }

    public void askReservationId() {
        System.out.println();
        System.out.println(SEPARATOR);
        System.out.println("예약 아이디를 입력해주세요.");
    }

    public void printReservations(List<ReservationResponse> reservations) {
        System.out.println();
        System.out.println(SEPARATOR);
        System.out.println("[ 예약 목록 ]");
        for (int i = 0; i < reservations.size(); i++) {
            System.out.println(formatReservation(i + 1, reservations.get(i)));
        }
    }

    public void printReservationTimes(List<ReservationTimeResponse> reservationTimes) {
        System.out.println();
        System.out.println(SEPARATOR);
        System.out.println("[ 예약 시간 목록 ]");
        reservationTimes.forEach(reservationTime ->
            System.out.println(formatReservationTime(reservationTime.id(), reservationTime)));
    }

    public void printReservation(ReservationResponse reservation) {
        System.out.println();
        System.out.println(SEPARATOR);
        System.out.println(formatReservation(reservation.id(), reservation));
    }

    public void printReservationTime(ReservationTimeResponse reservationTime) {
        System.out.println();
        System.out.println(SEPARATOR);
        System.out.println(formatReservationTime(reservationTime.id(), reservationTime));
    }

    private String formatReservation(long index, ReservationResponse r) {
        return String.format("%d. [%s] %s | %s", index, r.time().startAt(), r.name(), r.date());
    }

    private String formatReservationTime(long index, ReservationTimeResponse t) {
        return String.format("%d. [ID: %d] %s", index, t.id(), t.startAt());
    }
}