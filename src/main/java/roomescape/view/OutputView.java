package roomescape.view;

import java.util.List;
import roomescape.dto.ReservationTimeResponse;

public class OutputView {
    public void printStartMessage() {
        System.out.println("[🚪 방탈출 관리 페이지]");
    }

    public void printNoReservation() {
        System.out.println("예약이 없습니다.");
        System.out.println();
    }

    public void printNoReservationTime() {
        System.out.println("시간이 없습니다.");
        System.out.println();
    }

    public void printReservationTimes(List<ReservationTimeResponse> reservationTimes) {
        System.out.println("시간 번호 | 시간");
        reservationTimes.forEach(this::printReservationTime);
        System.out.println();
    }

    private void printReservationTime(ReservationTimeResponse reservationTime) {
        System.out.println(reservationTime.id() + " | " + reservationTime.startAt());
    }
}
