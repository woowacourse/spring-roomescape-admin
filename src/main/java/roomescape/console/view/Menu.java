package roomescape.console.view;

public enum Menu {
    RESERVATION,
    RESERVATION_TIME,
    END;

    public static Menu from(int index) {
        for (Menu value : Menu.values()) {
            if (value.ordinal() == (index - 1)) {
                return value;
            }
        }
        throw new IllegalArgumentException("존재하지 않는 메뉴입니다.");
    }
}
