package roomescape.console.view;

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
            1, FunctionCommand.GET_ALL, 2, FunctionCommand.CREATE, 3, FunctionCommand.DELETE);

    public ReservationRequest inputReservation() {
        System.out.println();
        System.out.println("예약 정보를 입력해주세요. (형식 : 날짜, 예약자, 시간 ID)");
        System.out.println("ex) 2023-08-10, 브리, 1");

        List<String> reservation = List.of(inputString().split(DELIMITER));
        LocalDate date = LocalDate.parse(reservation.get(0).trim(), DATE_FORMATTER);
        String name = reservation.get(1).trim();
        Long timeId = Long.parseLong(reservation.get(2).trim());

        return new ReservationRequest(date, name, timeId);
    }

    public ReservationTimeRequest inputReservationTime() {
        System.out.println();
        System.out.println("예약 시간 정보를 입력해주세요. (형식 : 시간");
        System.out.println("ex) 09:50");

        String reservationTime = inputString();
        LocalTime startAt = LocalTime.parse(reservationTime.trim(), TIME_FORMATTER);

        return new ReservationTimeRequest(startAt);
    }

    public DomainCommand inputChoosingDomain() {
        System.out.println();
        System.out.println("도메인을 선택해주세요.");
        System.out.println("1 - 예약 관리, 2 - 예약 시간 관리");
        return INT_TO_DOMAIN_COMMAND.get(inputInt());
    }

    public FunctionCommand inputChoosingFunction() {
        System.out.println();
        System.out.println("기능을 선택해주세요");
        System.out.println("1 - 전체 조회, 2 - 생성, 3 - 삭제");
        return INT_TO_FUNCTION_COMMAND.get(inputInt());
    }

    public Long inputDeletingId() {
        System.out.println();
        System.out.println("삭제할 ID를 입력해주세요.");
        return inputId();
    }

    private String inputString() {
        return SCANNER.nextLine();
    }

    private int inputInt() {
        return Integer.parseInt(SCANNER.nextLine());
    }

    private Long inputId() {
        return Long.parseLong(SCANNER.nextLine());
    }
}
