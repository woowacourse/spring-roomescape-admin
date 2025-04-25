package roomescape.view;

import java.util.List;
import org.springframework.stereotype.Component;
import roomescape.dto.ReservationResponseDto;
import roomescape.dto.ReservationTimeResponseDto;

@Component
public class OutputView {

    public void printStartMessage() {
        System.out.println("=== 방탈출 예약 콘솔 프로그램 ===");
        System.out.println();
    }

    public void printSuccessToCreateReservationTime(ReservationTimeResponseDto reservationTime) {
        System.out.println("예약 시간 생성을 완료했습니다.");
        System.out.println("생성된 예약 시간 : " + reservationTime.startAt());
        System.out.println();
    }

    public void printReservationTimes(List<ReservationTimeResponseDto> reservationTimes) {
        System.out.println("예약 시간 목록입니다.");
        System.out.println("id | 시작 시간");
        reservationTimes.forEach(time -> System.out.println(time.id() + " | " + time.startAt()));
        System.out.println();
    }

    public void printSuccessToDeleteReservationTime() {
        System.out.println("예약 시간 삭제를 완료했습니다.");
        System.out.println();
    }

    public void printSuccessToCreateReservation(ReservationResponseDto reservation) {
        System.out.println("예약 생성을 완료했습니다.");
        System.out.println("id | 예약자명 | 예약 날짜 | 예약 시간");
        System.out.printf("%d | %s | %s | %s\n",
                reservation.id(), reservation.name(),
                reservation.date(), reservation.time().startAt());
        System.out.println();
    }

    public void printReservations(List<ReservationResponseDto> reservations) {
        System.out.println("예약 목록입니다.");
        System.out.println("id | 예약자명 | 예약 날짜 | 예약 시간");
        reservations.forEach(reservation ->
                System.out.printf("%d | %s | %s | %s \n",
                        reservation.id(),
                        reservation.name(),
                        reservation.date(),
                        reservation.time().startAt()
                )
        );
        System.out.println();
    }

    public void printSuccessToDeleteReservation() {
        System.out.println("예약 삭제를 완료했습니다.");
        System.out.println();
    }
}
