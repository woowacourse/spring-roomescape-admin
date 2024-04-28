package roomescape.console.view;

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
}
