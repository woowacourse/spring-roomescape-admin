package roomescape.view;

import java.util.List;
import roomescape.controller.dto.ReservationResponse;
import roomescape.controller.dto.TimeSlotCreationResponse;

public class OutputView {

    private OutputView() {
    }

    public static void printAdminMenu() {
        System.out.println("==  방탈출 어드민 메뉴 == ");
        System.out.println("1. 예약 관리");
        System.out.println("2. 시각 관리");
        System.out.println("0. 프로그램 종료");
        System.out.println();
        printSelectMessage();
    }

    public static void printReservationManagementMenu() {
        System.out.println("== 예약 관리 메뉴 ==");
        System.out.println("1. 예약 추가");
        System.out.println("2. 예약 취소");
        System.out.println("0. 뒤로 가기");
        System.out.println();
        printSelectMessage();
    }

    public static void printTimeSlotManagementMenu() {
        System.out.println("== 시각 관리 메뉴 ==");
        System.out.println("1. 시각 추가");
        System.out.println("2. 시각 삭제");
        System.out.println("0. 뒤로 가기");
        System.out.println();
        printSelectMessage();
    }

    private static void printSelectMessage() {
        System.out.println("## 원하는 기능을 선택하세요.");
    }

    public static void printReservations(List<ReservationResponse> responses) {
        System.out.println("== 예약 목록 ==");
        System.out.printf("%10s | %10s | %10s | %10s%n", "ID", "Name", "Date", "Time");
        System.out.println("=".repeat(50));
        for (ReservationResponse response : responses) {
           printSingleReservation(response);
        }
    }

    private static void printSingleReservation(ReservationResponse response) {
        System.out.printf("%10d | %10s | %10s | %10s%n",
                response.id(),
                response.name(),
                response.date(),
                response.timeSlot().startAt());
        System.out.println("=".repeat(50));
    }

    public static void printTimeSlots(List<TimeSlotCreationResponse> responses) {
        System.out.println("== 시각 목록 ==");
        System.out.printf("%10s | %10s%n", "ID", "시각");
        for (TimeSlotCreationResponse response : responses) {
            printSingleTimeSlot(response);
        }
    }

    private static void printSingleTimeSlot(TimeSlotCreationResponse response) {
        System.out.printf("%10d | %10s%n",
                response.id(),
                response.startAt());
    }

    public static void printReservationCreationResponse(ReservationResponse response) {
        System.out.println("예약이 완료되었습니다.");
        System.out.printf("ID: %d, 이름: %s, 날짜: %s, 시간: %s%n",
                response.id(),
                response.name(),
                response.date(),
                response.timeSlot().startAt());
    }

    public static void printTimeSlotCreationResponse(TimeSlotCreationResponse response) {
        System.out.println("시각이 추가되었습니다.");
        System.out.printf("ID: %d, 시간: %s%n",
                response.id(),
                response.startAt());
    }

    public static void printDeleteReservationMessage() {
        System.out.println("예약이 취소되었습니다.");
    }

    public static void printDeleteTimeSlotMessage() {
        System.out.println("시각이 삭제되었습니다.");
    }
}
