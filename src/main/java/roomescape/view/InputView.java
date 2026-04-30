package roomescape.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationTimeRequest;

public class InputView {

    private static final Scanner SCANNER = new Scanner(System.in);

    public InputMenu readMenu() {
        int menuNumber = parseInt(readStrippedLine());
        return InputMenu.from(menuNumber);
    }

    public ReservationTimeRequest readReservationTime() {
        return new ReservationTimeRequest(readStrippedLine());
    }

    public long readLongId() {
        return parseLong(readStrippedLine());
    }

    public ReservationRequest readReservation() {
        String[] parts = readStrippedLine().split(",");
        if (parts.length != 3) {
            throw new IllegalArgumentException("예약 정보는 이름, 날짜, 시간ID 순으로 입력해주세요.");
        }
        String name = validateName(parts[0].strip());
        String date = validateDate(parts[1].strip());
        Long timeId = validateTimeId(parseLong(parts[2].strip()));
        return new ReservationRequest(name, date, timeId);
    }

    private String validateName(final String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("이름은 빈 값일 수 없습니다.");
        }
        return name;
    }

    private String validateDate(final String date) {
        if (date.isBlank()) {
            throw new IllegalArgumentException("날짜는 빈 값일 수 없습니다.");
        }
        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("날짜는 yyyy-MM-dd 형식으로 입력해주세요. (예: 2026-04-30)");
        }
        return date;
    }

    private long validateTimeId(final long timeId) {
        if (timeId <= 0) {
            throw new IllegalArgumentException("시간 ID는 양수여야 합니다.");
        }
        return timeId;
    }

    private int parseInt(final String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자만 입력할 수 있습니다. 다시 입력해주세요.");
        }
    }

    private String readStrippedLine() {
        final String input = SCANNER.nextLine();
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("유효하지 않은 입력입니다. 다시 입력해주세요.");
        }
        return input.strip();
    }

    private long parseLong(final String input) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자만 입력할 수 있습니다. 다시 입력해주세요.");
        }
    }
}