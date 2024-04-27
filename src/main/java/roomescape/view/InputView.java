package roomescape.view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputView {
    private static final Scanner SCANNER = new Scanner(System.in);

    public String readOptions() {
        return SCANNER.nextLine();
    }

    public LocalTime readTime() {
        try {
            return LocalTime.parse(SCANNER.nextLine());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[ERROR] 잘못된 형식의 시간입니다.");
        }
    }

    public LocalDate readDate() {
        try {
            return LocalDate.parse(SCANNER.nextLine());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[ERROR] 잘못된 형식의 시간입니다.");
        }
    }

    public Long readId() {
        try {
            return SCANNER.nextLong();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 잘못된 형식의 id입니다.");
        }
    }

    public String readName() {
        return SCANNER.nextLine();
    }
}
