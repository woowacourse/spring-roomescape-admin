package roomescape.console.view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import roomescape.core.service.dto.ReservationRequest;
import roomescape.core.service.dto.ReservationTimeRequest;

public class InputView {
    private static final Scanner SCANNER = new Scanner(System.in);

    public Command askCommand() {
        String input = SCANNER.next();
        return Command.findByInput(input);
    }

    public ReservationRequest askReservationContentToSave() {
        System.out.println("""
                > 추가할 예약의 정보를 {이름,날짜(yyyy-MM-dd),시간ID} 형식에 맞춰 입력해 주세요.
                > ex. 도라,2024-04-29,1
                """);
        String input = SCANNER.next();
        List<String> content = List.of(input.split(","));
        validateContentSize(content, 3);
        return new ReservationRequest(
                parseLocalDate(content.get(0)),
                content.get(1),
                parseLong(content.get(2))
        );
    }

    public Long askReservationIdToDelete() {
        System.out.println("> 삭제할 예약의 ID를 입력해 주세요.");
        String input = SCANNER.next();
        return parseLong(input);
    }

    public ReservationTimeRequest askReservationTimeContentToSave() {
        System.out.println("""
                > 추가할 시간의 정보를 {시간(HH:mm)} 형식에 맞춰 입력해 주세요.
                > ex. 15:05
                """);
        String input = SCANNER.next();
        return new ReservationTimeRequest(parseLocalTime(input));
    }

    public Long askReservationTimeIdToDelete() {
        System.out.println("> 삭제할 시간의 ID를 입력해 주세요.");
        String input = SCANNER.next();
        return parseLong(input);
    }

    private void validateContentSize(List<String> content, int contentSize) {
        if (content.size() != contentSize) {
            throw new IllegalArgumentException("필요한 정보를 모두 입력해 주세요.");
        }
    }

    private LocalDate parseLocalDate(String input) {
        try {
            return LocalDate.parse(input);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("날짜를 형식에 맞게 입력해 주세요.");
        }
    }

    private LocalTime parseLocalTime(String input) {
        try {
            return LocalTime.parse(input);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("시간을 형식에 맞게 입력해주세요.");
        }
    }

    private Long parseLong(String input) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자를 형식에 맞게 입력해 주세요.");
        }
    }
}
