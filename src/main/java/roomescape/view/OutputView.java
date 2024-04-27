package roomescape.view;

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
}
