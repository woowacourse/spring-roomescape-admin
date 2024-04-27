package roomescape.view;

import java.time.LocalTime;
import java.util.Scanner;

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

    public LocalTime readReservationTime() {
        System.out.println("[예약 시간 추가]");
        System.out.println("추가 할 예약 시간을 입력해주세요.(ex. 14:00)");
        String input = SCANNER.nextLine();
        return LocalTime.parse(input);
    }
}
