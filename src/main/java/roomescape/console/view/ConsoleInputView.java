package roomescape.console.view;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import roomescape.console.view.dto.DomainCommand;
import roomescape.console.view.dto.FunctionCommand;
import roomescape.domain.dto.ReservationRequest;
import roomescape.domain.dto.ReservationTimeRequest;

public class ConsoleInputView {

    private static final Scanner SCANNER = new Scanner(System.in);

    private static final String DELIMITER = ",";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private static final Map<Integer, DomainCommand> INT_TO_DOMAIN_COMMAND = Map.of(
            1, DomainCommand.RESERVATION, 2, DomainCommand.RESERVATION_TIME, 3, DomainCommand.END_PROGRAM);
    private static final Map<Integer, FunctionCommand> INT_TO_FUNCTION_COMMAND = Map.of(
            1, FunctionCommand.FIND_ALL, 2, FunctionCommand.CREATE, 3, FunctionCommand.DELETE);

    public ReservationRequest inputReservation() {
        System.out.println();
        System.out.println("예약 정보를 입력해주세요. (형식 : 날짜, 예약자, 시간 ID)");
        System.out.println("ex) 2023-08-10, 브리, 1");

        List<String> reservation = List.of(inputString().split(DELIMITER));
        LocalDate date = toLocalDate(reservation.get(0));
        String name = reservation.get(1).trim();
        Long timeId = toId(reservation.get(2));

        return new ReservationRequest(date, name, timeId);
    }

    public ReservationTimeRequest inputReservationTime() {
        System.out.println();
        System.out.println("예약 시간 정보를 입력해주세요. (형식 : 시간");
        System.out.println("ex) 09:50");

        String reservationTime = inputString();
        LocalTime startAt = toLocalTime(reservationTime);

        return new ReservationTimeRequest(startAt);
    }

    private String inputString() {
        return SCANNER.nextLine();
    }

    private LocalDate toLocalDate(String string) {
        try {
            return LocalDate.parse(string.trim(), DATE_FORMATTER);
        } catch (DateTimeException exception) {
            throw new IllegalArgumentException("날짜 형식이 일치하지 않습니다.", exception);
        }
    }

    private LocalTime toLocalTime(String string) {
        try {
            return LocalTime.parse(string.trim(), TIME_FORMATTER);
        } catch (DateTimeException exception) {
            throw new IllegalArgumentException("시간 형식이 일치하지 않습니다.", exception);
        }
    }

    private Long toId(String string) {
        try {
            return Long.parseLong(string);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("id 형식(양수)을 맞추어 입력해야 합니다.", exception);
        }
    }

    public DomainCommand chooseDomain() {
        System.out.println();
        System.out.println("도메인을 선택해주세요.");
        System.out.println("1 - 예약 관리, 2 - 예약 시간 관리, 3 - 프로그램 종료");
        return inputCommandBy(INT_TO_DOMAIN_COMMAND);
    }

    public FunctionCommand chooseFunction() {
        System.out.println();
        System.out.println("기능을 선택해주세요");
        System.out.println("1 - 전체 조회, 2 - 생성, 3 - 삭제");
        return inputCommandBy(INT_TO_FUNCTION_COMMAND);
    }

    private <T> T inputCommandBy(Map<Integer, T> commandMap) {
        T command = commandMap.get(inputInt());
        if (command == null) {
            throw new IllegalArgumentException("입력 형식이 일치하지 않습니다.");
        }
        return command;
    }

    private int inputInt() {
        try {
            return Integer.parseInt(SCANNER.nextLine());
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("정수 만을 입력해야 합니다.", exception);
        }
    }

    public Long inputDeletingId() {
        System.out.println();
        System.out.println("삭제할 ID를 입력해주세요.");
        return inputId();
    }

    private Long inputId() {
        try {
            return Long.parseLong(SCANNER.nextLine());
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("id 형식(양수)을 맞추어 입력해야 합니다.", exception);
        }
    }

}
