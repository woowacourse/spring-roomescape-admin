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

    public Long readRemoveTimeId() {
        System.out.println("삭제할 시간의 ID를 입력해주세요.");
        return readLong();
    }

    private void validateBlank(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("빈 값을 입력했습니다.");
        }
    }

    private void validateLong(String value) {
        try {
            Long.getLong(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("잘못된 값을 입력했습니다.");
        }
    }

    public String readName() {
        System.out.print("이름을 입력해주세요. : ");
        return readString();
    }

    public String readDate() {
        System.out.print("날짜를 입력해주세요. : ");
        return readString();
    }

    public Long readTimeId() {
        System.out.print("시간 ID를 입력해주세요. : ");
        return readLong();
    }

    private String readString() {
        String value = scanner.next();
        validateBlank(value);
        return value;
    }

    private Long readLong() {
        String value = scanner.next();
        validateBlank(value);
        validateLong(value);
        return Long.parseLong(value);
    }
}
