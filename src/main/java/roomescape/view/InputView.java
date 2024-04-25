package roomescape.view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import roomescape.controller.dto.ReservationRequest;
import roomescape.controller.dto.TimeSlotCreationRequest;
import roomescape.view.command.AdminCommand;
import roomescape.view.command.ManagementCommand;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);

    private InputView() {
    }

    public static AdminCommand getAdminCommand() {
        String input = scanner.nextLine();
        return AdminCommand.from(input);
    }

    public static ManagementCommand getManagementCommand() {
        String input = scanner.nextLine();
        return ManagementCommand.from(input);
    }

    public static TimeSlotCreationRequest createTimeSlotRequest() {
        System.out.println("추가할 시간을 24시간 단위로 입력하세요. ex) 18:30");
        String time = inputTime();
        return new TimeSlotCreationRequest(time);
    }

    public static ReservationRequest createReservationRequest() {
        System.out.println("예약할 사람의 이름을 입력하세요.");
        String name = scanner.nextLine();
        System.out.println("예약할 날짜를 입력하세요. ex) 2021-07-01");
        String date = inputDate();
        System.out.println("예약할 시간 ID를 입력하세요.");
        long time = parseLong(scanner.nextLine());
        return new ReservationRequest(name, date, time);
    }

    private static String inputDate() {
        String date = scanner.nextLine();
        validateDateParsable(date);
        return date;
    }

    private static String inputTime() {
        String time = scanner.nextLine();
        validateTimeParsable(time);
        return time;
    }

    public static long inputDeleteReservationId() {
        System.out.println("삭제할 예약 ID를 입력하세요.");
        return parseLong(scanner.nextLine());
    }

    public static long inputDeleteTimeSlotId() {
        System.out.println("삭제할 시간 ID를 입력하세요.");
        return parseLong(scanner.nextLine());
    }

    private static long parseLong(String input) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("입력 형식이 올바르지 않습니다.");
        }
    }

    private static void validateTimeParsable(String time) {
        try {
            LocalTime.parse(time);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("시간 형식이 올바르지 않습니다.");
        }
    }

    private static void validateDateParsable(String date) {
        try {
            LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("날짜 형식이 올바르지 않습니다.");
        }
    }
}
