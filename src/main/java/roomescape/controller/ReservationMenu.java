package roomescape.controller;

import java.util.Arrays;

public enum ReservationMenu {
    ADD_RESERVATION(1),
    DELETE_RESERVATION(2),
    ADD_RESERVATION_TIME(3),
    DELETE_RESERVATION_TIME(4),
    END(5);

    private final int menuOrder;

    ReservationMenu(int menuOrder) {
        this.menuOrder = menuOrder;
    }

    public static ReservationMenu findReservationMenu(int selectedMenu) {
        return Arrays.stream(ReservationMenu.values())
                .filter(menu -> menu.menuOrder == selectedMenu)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메뉴입니다."));
    }
}
