package roomescape.view;

import org.springframework.stereotype.Component;
import roomescape.entity.ReservationTime;

@Component
public class OutputView {

    public void printStartMessage() {
        System.out.println("=== 방탈출 예약 콘솔 프로그램 ===");
        System.out.println();
    }

    public void printSuccessToCreateReservationTime(ReservationTime reservationTime) {
        System.out.println("예약 시간 생성을 완료했습니다.");
        System.out.println("생성된 예약 시간 : " + reservationTime.getStartAt());
        System.out.println();
    }
}
