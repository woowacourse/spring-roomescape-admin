package roomescape.console.view;

import java.util.List;
import roomescape.core.dto.ReservationResponse;
import roomescape.core.dto.ReservationTimeResponse;

public class OutputView {
    public void printReservations(List<ReservationResponse> reservationResponses) {
        System.out.println("\n[방탈출 예약 현황]");
        if (reservationResponses.isEmpty()) {
            System.out.println("현재 예약 사항이 존재하지 않습니다.");
            return;
        }
        reservationResponses.forEach(this::printReservation);
    }

    private void printReservation(ReservationResponse reservationResponse) {
        System.out.printf("(%d) %s | %s | %s\n",
                reservationResponse.getId(), reservationResponse.getName(), reservationResponse.getDate(),
                reservationResponse.getTime().getStartAt());
    }

    public void printReservationTimes(List<ReservationTimeResponse> reservationTimeResponses) {
        System.out.println("\n[방탈출 예약 시간 현황]");
        if (reservationTimeResponses.isEmpty()) {
            System.out.println("현재 예약 시간이 존재하지 않습니다.");
        }
        reservationTimeResponses.forEach(this::printReservationTime);
    }

    private void printReservationTime(ReservationTimeResponse reservationTimeResponse) {
        System.out.printf("(%d) %s\n",
                reservationTimeResponse.getId(), reservationTimeResponse.getStartAt());
    }

    public void printReservationCreate(ReservationResponse reservationResponse) {
        System.out.println("\n[예약 성공]");
        System.out.println("예약이 완료 되었습니다.");
        printReservation(reservationResponse);
    }

    public void printReservationDelete() {
        System.out.println("\n[예약 삭제 완료]");
    }

    public void printReservationTimeCreate(ReservationTimeResponse reservationTimeResponse) {
        System.out.println("\n[예약 시간 추가 완료]");
        System.out.println("예약 시간이 추가되었습니다.");
        printReservationTime(reservationTimeResponse);
    }

    public void printReservationTimeDelete() {
        System.out.println("\n[예약 시간 삭제 완료]");
    }
}
