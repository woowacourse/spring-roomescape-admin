package roomescape.view;

import java.time.LocalDate;
import java.time.LocalTime;
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
        String input = readStrippedLine();
        LocalTime startAt = parseTime(input);
        return new ReservationTimeRequest(startAt);
    }

    public long readLongId() {
        return parseLong(readStrippedLine());
    }

    public ReservationRequest readReservation() {
        String[] parts = readStrippedLine().split(",");
        if (parts.length != 3) {
            throw new IllegalArgumentException("예약 정보는 이름, 날짜, 시간ID 순으로 입력해주세요.");
        }
        String name = parseName(parts[0].strip());
        LocalDate date = parseDate(parts[1].strip());
        long timeId = parseTimeId(parts[2].strip());
        return new ReservationRequest(name, date, timeId);
    }

    private String parseName(final String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("이름은 빈 값일 수 없습니다.");
        }
        return name;
    }

    private LocalDate parseDate(final String date) {
        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("날짜는 yyyy-MM-dd 형식으로 입력해주세요. (예: 2026-04-30)");
        }
    }

    private LocalTime parseTime(final String value) {
        try {
            return LocalTime.parse(value, DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("시작 시간은 HH:mm 형식으로 입력해주세요. (예: 09:00)");
        }
    }

    private long parseTimeId(final String value) {
        try {
            long timeId = Long.parseLong(value);
            if (timeId <= 0) {
                throw new IllegalArgumentException("시간 ID는 양수여야 합니다.");
            }
            return timeId;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("시간 ID는 숫자여야 합니다.");
        }
    }

    private String readStrippedLine() {
        final String input = SCANNER.nextLine();
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("유효하지 않은 입력입니다. 다시 입력해주세요.");
        }
        return input.strip();
    }

    private int parseInt(final String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자만 입력할 수 있습니다. 다시 입력해주세요.");
        }
    }

    private long parseLong(final String input) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자만 입력할 수 있습니다. 다시 입력해주세요.");
        }
    }
}