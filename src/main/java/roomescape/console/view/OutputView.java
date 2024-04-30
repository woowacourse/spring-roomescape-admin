package roomescape.console.view;

import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.CreateReservationResponse;
import roomescape.dto.CreateReservationTimeResponse;

import java.util.List;

public class OutputView {

    public void announceSuccessfulReservation(CreateReservationResponse response) {
        System.out.println("예약이 성공적으로 등록되었습니다.");
        System.out.println(formatReservation(response));
    }

    public void announceSuccessfulReservationTime(CreateReservationTimeResponse response) {
        System.out.println("시간이 성공적으로 등록되었습니다.");
        System.out.println(formatReservationTime(response));
    }

    public void announceReservations(List<Reservation> reservations) {
        System.out.println("<예약 정보 목록>");
        for (Reservation reservation : reservations) {
            System.out.println("- " + String.format(
                    """
                            예약 번호: %d
                            예약 이름: %s
                            예약 날짜: %s
                            예약 시간: %s""",
                    reservation.getId(),
                    reservation.getName(),
                    reservation.getDate(),
                    reservation.getTime().getStartAt()));
        }
    }

    public void announceReservationTimes(List<ReservationTime> reservationTimes) {
        System.out.println("<시간 등록 목록>");
        for (ReservationTime reservationTime : reservationTimes) {
            System.out.println("- " + String.format(
                    "시간 번호: %d 시간: %s",
                    reservationTime.getId(),
                    reservationTime.getStartAt()
            ));
        }
    }

    public void announceReservationDeletion() {
        System.out.println("예약이 성공적으로 삭제되었습니다.");
    }

    public void announceReservationTimeDeletion() {
        System.out.printf("예약시간이 성공적으로 삭제되었습니다.");
    }

    public void announceExit() {
        System.out.println("===============예약 프로그램이 종료되었습니다.===============");
    }

    public static void printError(String message) {
        System.out.println("[ERROR] " + message);
    }

    private String formatReservation(CreateReservationResponse reservationResponse) {
        return String.format(
                """
                        예약 번호: %d
                        예약 이름: %s
                        예약 날짜: %s
                        예약 시간: %s""",
                reservationResponse.id(),
                reservationResponse.name(),
                reservationResponse.date(),
                reservationResponse.reservationTime().getStartAt()
        );
    }

    private String formatReservationTime(CreateReservationTimeResponse reservationTimeResponse) {
        return String.format(
                "시간 번호: %d 시간: %s",
                reservationTimeResponse.id(),
                reservationTimeResponse.startAt()
        );
    }
}
