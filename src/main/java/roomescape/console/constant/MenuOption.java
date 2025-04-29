package roomescape.console.constant;

import java.util.Arrays;

public enum MenuOption {

    RESERVATION_CREATE("1"),
    RESERVATION_DELETE("2"),
    RESERVATION_TIME_CREATE("3"),
    RESERVATION_TIME_DELETE("4"),
    EXIT("5");

    private final String option;

    MenuOption(String option) {
        this.option = option;
    }

    public static MenuOption findMenuOption(String input) {
        return Arrays.stream(MenuOption.values())
                .filter(menuOption -> menuOption.option.equals(input))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("옵션을 정확히 선택해주세요"));
    }
}
