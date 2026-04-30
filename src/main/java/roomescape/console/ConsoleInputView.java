package roomescape.console;

import roomescape.controller.dto.request.ReservationRequest;
import roomescape.controller.dto.request.ReservationTimeRequest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ConsoleInputView {

    private static final Scanner sc = new Scanner(System.in);

    public static int readOption() {
        System.out.println("선택하고 싶은 옵션을 선택해주세요: (1~7)");
        System.out.println("1. 시간 추가");
        System.out.println("2. 시간 목록 조회");
        System.out.println("3. 특정 ID의 시간 삭제");
        System.out.println("4. 예약 추가");
        System.out.println("5. 예약 목록 조회");
        System.out.println("6. 특정 ID의 예약 삭제");
        System.out.println("7. 종료");

        return parseAndValidateOption(sc.nextLine());
    }

    private static int parseAndValidateOption(String strOption) {
        try {
            int i = Integer.parseInt(strOption);
            if(i < 1 || i > 6) {
                throw new RuntimeException("1~7 사이의 숫자를 입력해주세요.");
            }
            return i;
        } catch (NumberFormatException e) {
            throw new RuntimeException("숫자 형식으로 입력해주세요.");
        }
    }

    public static ReservationTimeRequest readTimeRequest() {
        System.out.println("추가하고 싶은 시간을 입력해주세요. 형식: HH:MM:SS (SS 생략 가능)");
        LocalTime startAt = null;
        try {
            startAt = LocalTime.parse(sc.nextLine());
        } catch (DateTimeParseException e) {
            throw new RuntimeException("변환할 수 없는 포멧입니다. 올바른 형식: HH:MM:SS(SS 생략 가능)");
        }
        return new ReservationTimeRequest(startAt);
    }

    public static Long readDeleteTimeId() {
        System.out.println("삭제하고 싶은 시간 ID를 입력해주세요: ");
        return readLongTypeId();
    }

    public static ReservationRequest readReservationRequest() {
        System.out.println("추가하고 싶은 날짜를 입력해주세요: (형식: YYYY-MM-DD)");
        LocalDate date = readDate();
        System.out.println("이름을 입력해주세요.");
        String name = sc.nextLine();
        System.out.println("timeId를 입력해주세요.");
        long timeId = readLongTypeId();

        return new ReservationRequest(date, name, timeId);
    }

    public static Long readDeleteReservationId() {
        System.out.println("삭제하고 싶은 예약 ID를 입력해주세요: ");
        return readLongTypeId();
    }

    private static LocalDate readDate() {
        LocalDate date = null;
        try {
            date = LocalDate.parse(sc.nextLine());
        } catch (DateTimeParseException e) {
            throw new RuntimeException("변환할 수 없는 포멧입니다. (올바른 형식: YYYY-MM-DD)");
        }
        return date;
    }

    private static long readLongTypeId() {
        String strId = sc.nextLine();
        long id;
        try {
            id = Long.parseLong(strId);
        } catch (NumberFormatException e) {
            throw new RuntimeException("숫자 형식으로 입력해주세요.");
        }
        return id;
    }

}
