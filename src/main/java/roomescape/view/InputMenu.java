
package roomescape.view;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum InputMenu {

    UNSELECTED(-1, "선택된 메뉴 없음"),
    EXIT(0, "종료하기"),
    TIMES_GET_ALL(1, "시간 목록 조회하기"),
    TIMES_ADD(2, "시간 추가하기"),
    TIMES_DELETE(3, "시간 삭제하기"),
    RESERVATION_GET_ALL(4, "예약 목록 조회하기"),
    RESERVATION_ADD(5, "예약 추가하기"),
    RESERVATION_DELETE(6, "예약 삭제하기"),
    ;

    private final int number;
    private final String display;

    InputMenu(int number, String display) {
        this.number = number;
        this.display = display;
    }

    public static InputMenu none() {
        return UNSELECTED;
    }

    public static InputMenu from(final int menuNumber) {
        return Arrays.stream(InputMenu.values())
            .filter(inputMenu -> inputMenu.number == menuNumber)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메뉴 번호 입니다."));
    }

    public static String getDisplayMenus() {
        return Arrays.stream(InputMenu.values())
            .filter(inputMenu -> inputMenu != UNSELECTED)
            .map(inputMenu -> inputMenu.number + ". " + inputMenu.display)
            .collect(Collectors.joining("\n"));
    }

    public boolean isExit() {
        return this == EXIT;
    }
}
