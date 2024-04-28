package roomescape.console.view;

import java.util.List;
import roomescape.dto.ReservationTimeResponse;

public class OutputView {
    public void printStartMessage() {
        System.out.println("=== 방탈출 어드민 프로그램을 시작합니다 ===");
    }

    public void printAdminFunction() {
        System.out.println("[ 관리자 메뉴 목록 ]");
        System.out.println("1. 예약 조회");
        System.out.println("2. 예약 추가");
        System.out.println("3. 예약 삭제");
        System.out.println("4. 예약 시간 조회");
        System.out.println("5. 예약 시간 추가");
        System.out.println("6. 예약 시간 삭제");
        System.out.println("7. 프로그램 종료");
        System.out.println();
    }

    public void printAllReservationTimes(List<ReservationTimeResponse> allReservationTimes) {
        if (allReservationTimes.size() == 0) {
            System.out.println("존재하는 예약 시간이없습니다.");
        }
        System.out.println("[예약시간 목록]");
        for (ReservationTimeResponse response : allReservationTimes) {
            System.out.printf("id: %d, start-at: %s", response.id(), response.startAt());
            System.out.println();
        }
        System.out.println();
    }

    public void printCreateTimeSuccessMessage(ReservationTimeResponse response) {
        System.out.printf("예약 시간 생성이 완료되었습니다. id: %d, start-at: %s", response.id(), response.startAt());
        System.out.println();
    }

    public void printDeleteTimeSuccessMessage() {
        System.out.printf("예약 시간 삭제가 완료되었습니다.");
        System.out.println();
    }
}
