package roomescape.view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import roomescape.dto.ReservationRequest;

public class InputView {
    private static final Scanner SCANNER = new Scanner(System.in);

    public int readSelectedMenu() {
        System.out.println("""
                메뉴를 선택해 주세요(ex. 1)
                1. 예약 추가
                2. 예약 삭제
                3. 예약 시간 추가
                4. 예약 시간 삭제
                """);
        String input = SCANNER.nextLine();
        return Integer.parseInt(input);
    }

    public ReservationRequest readReservationRequest() {
        System.out.println("예약자를 입력해주세요.");
        String name = SCANNER.nextLine();
        System.out.println("예약 날짜를 입력해주세요.(ex. 2024-04-27)");
        LocalDate date = LocalDate.parse(SCANNER.nextLine());
        System.out.println("예약 시간을 선택해주세요.(ex. 1)");
        long timeId = Long.parseLong(SCANNER.nextLine());
        return new ReservationRequest(name, date, timeId);
    }

    public long readDeleteReservationId() {
        System.out.println("삭제할 예약 번호를 입력해주세요.");
        String input = SCANNER.nextLine();
        return Long.parseLong(input);
    }

    public LocalTime readReservationTime() {
        System.out.println("[예약 시간 추가]");
        System.out.println("추가 할 예약 시간을 입력해주세요.(ex. 14:00)");
        String input = SCANNER.nextLine();
        return LocalTime.parse(input);
    }

    public long readDeleteReservationTimeId() {
        System.out.println("삭제할 예약 시간 번호를 입력해주세요.");
        String input = SCANNER.nextLine();
        return Long.parseLong(input);
    }
}
