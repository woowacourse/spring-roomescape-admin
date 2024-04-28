package roomescape.view;

import java.util.List;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

public class OutputView {

    private static final String COMMANDS_MESSAGE = """
            방탈출 관리 시스템 기능입니다.
            1 방탈출 예약 조회
            2 방탈출 예약시간 조회
            3 방탈출 예약 추가
            4 방탈출 예약시간 추가
            5 방탈출 예약 삭제
            6 방탈출 예약시간 삭제
            7 종료
            """;
    private static final String RESERVATIONS_MESSAGE = "방탈출 예약 내역입니다.\n예약번호\t예약자\t\t날짜\t\t\t\t시간";
    private static final String RESERVATION_TIMES_MESSAGE = "방탈출 시간 내역입니다.\n순서\t\t시간";
    private static final String RESERVATIONS_TEMPLATE = "%s\t\t%s\t\t%s\t\t%s";
    private static final String RESERVATION_TIMES_TEMPLATE = "%s\t\t%s";


    public void printCommands() {
        System.out.print(COMMANDS_MESSAGE);
    }

    public void printEnd() {
        System.out.println("방탈출 예약관리 기능이 종료되었습니다.");
    }

    public void printAllReservations(List<Reservation> reservations) {
        System.out.println(RESERVATIONS_MESSAGE);
        for (Reservation reservation : reservations) {
            printReservation(reservation);
        }
    }

    private void printReservation(Reservation reservation) {
        System.out.println(String.format(RESERVATIONS_TEMPLATE,
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime().getStartAt()));
    }

    public void printAllReservationTimes(List<ReservationTime> reservationTimes) {
        System.out.println(RESERVATION_TIMES_MESSAGE);
        for (ReservationTime reservationTime : reservationTimes) {
            printReservationTime(reservationTime);
        }
    }

    private void printReservationTime(ReservationTime reservationTime) {
        System.out.println(String.format(RESERVATION_TIMES_TEMPLATE,
                reservationTime.getId(),
                reservationTime.getStartAt()));
    }

    public void printReservationComplete() {
        System.out.println("예약이 완료 되었습니다.");
    }

    public void printAddReservationTime() {
        System.out.println("예약시간이 추가되었습니다.");
    }

    public void printReservationDelete() {
        System.out.println("방탈출 예약이 삭제되었습니다.");
    }

    public void printReservationTimeDelete() {
        System.out.println("예약시간이 삭제되었습니다.");
    }
}
