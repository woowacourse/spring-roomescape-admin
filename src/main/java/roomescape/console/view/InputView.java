package roomescape.console.view;

import java.util.Scanner;

public class InputView {

    private Scanner scanner = new Scanner(System.in);

    public String readMenu() {
        System.out.println("""
                메뉴를 입력해주세요.
                1. 시간 추가
                2. 예약 추가
                3. 시간 삭제
                4. 예약 삭제
                5. 시간 확인
                6. 예약 확인
                
                종료를 원하면 "exit"을 입력하세요
                """);
        return readString();
    }

    public String readTime() {
        System.out.println("추가할 시간을 입력해주세요. (ex. 12:00)");
        return readString();
    }

    private String readString() {
        String value = scanner.next();
        validateBlank(value);
        return value;
    }

    private void validateBlank(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("빈 값을 입력했습니다.");
        }
    }
}
