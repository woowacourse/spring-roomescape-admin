package roomescape.console.view;

import java.util.List;
import java.util.stream.Collectors;
import roomescape.core.service.dto.ReservationResponse;
import roomescape.core.service.dto.ReservationTimeResponse;

public class OutputView {
    public void printStartIntro() {
        System.out.println("방탈출 예약 관리 프로그램을 시작합니다.");
    }

    public void printCommand() {
        System.out.println("""
                > 원하는 기능의 번호를 입력해 주세요.
                > 1. 예약 목록 조회
                > 2. 예약 추가
                > 3. 예약 삭제
                > 4. 시간 목록 조회
                > 5. 시간 추가
                > 6. 시간 삭제
                """);
    }

    public void printReservation(ReservationResponse reservation) {
        System.out.println(formatReservation(reservation));
    }

    public void printAllReservation(List<ReservationResponse> reservations) {
        if (reservations.isEmpty()) {
            printEmpty();
            return;
        }
        System.out.println(formatReservations(reservations));
    }

    public void printReservationTime(ReservationTimeResponse reservationTime) {
        System.out.println(formatReservationTime(reservationTime));
    }

    public void printAllReservationTime(List<ReservationTimeResponse> reservationTimes) {
        if (reservationTimes.isEmpty()) {
            printEmpty();
            return;
        }
        System.out.println(formatReservationTimes(reservationTimes));
    }

    private void printEmpty() {
        System.out.println("[]");
    }

    private String formatReservation(ReservationResponse reservation) {
        return String.format("""
                        {
                            id: %d,
                            name: %s,
                            date: %s,
                            time: {
                                id: %d,
                                startAt: %s
                            }
                        }
                        """,
                reservation.id(),
                reservation.name(),
                reservation.name(),
                reservation.time().id(),
                reservation.time().startAt()
        );
    }

    private String formatReservations(List<ReservationResponse> reservations) {
        return reservations.stream()
                .map(this::formatReservation)
                .collect(Collectors.joining(",\n", "[\n", "\n]"));
    }

    private String formatReservationTime(ReservationTimeResponse reservationTime) {
        return String.format("""
                        {
                            id: %d,
                            startAt: %s
                        }
                        """,
                reservationTime.id(),
                reservationTime.startAt()
        );
    }

    private String formatReservationTimes(List<ReservationTimeResponse> reservationTimes) {
        return reservationTimes.stream()
                .map(this::formatReservationTime)
                .collect(Collectors.joining(",\n", "[\n", "\n]"));
    }
}
