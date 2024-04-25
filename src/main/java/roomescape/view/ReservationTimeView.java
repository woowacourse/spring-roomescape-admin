package roomescape.view;

import java.time.LocalTime;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ReservationTimeView {
    private final Scanner scanner = new Scanner(System.in);

    private static final Pattern TIME_PATTERN = Pattern.compile("^\\d+:\\d+$");

    public LocalTime readStartAt() {
        System.out.println("[INFO] 방탈출 예약이 가능한 시간을 추가해주세요. (ex. 23:30)");
        String rawReservationTime = scanner.nextLine();
        TIME_PATTERN.matcher(rawReservationTime);

        return LocalTime.parse(rawReservationTime);
    }

    public void printSuccessfullyAdded() {
        System.out.println("[INFO] 예약 가능 시간이 추가되었습니다.");
    }
}
