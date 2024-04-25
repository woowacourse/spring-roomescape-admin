package roomescape.console.view;

import java.util.Arrays;

public enum AdminMenu {

    RESERVATION_TIME(1),
    RESERVATION(2),
    QUIT(3);

    private final int menuNumber;

    AdminMenu(int menuNumber) {
        this.menuNumber = menuNumber;
    }

    public static AdminMenu from(int inputNumber) {
        return Arrays.stream(AdminMenu.values())
                .filter(adminMenu -> adminMenu.menuNumber == inputNumber)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 명령어입니다."));
    }
}
