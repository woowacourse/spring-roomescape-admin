package roomescape.fixture;

import roomescape.domain.Theme;

public abstract class ThemeFixture {

    public static final int INITIAL_THEME_SIZE = 3;
    public static final long THEME_1_ID = 1;
    public static final long THEME_2_ID = 2;
    public static final long NO_RESERVATION_THEME_ID = 3;

    public static Theme theme1() {
        return new Theme(THEME_1_ID, "theme1", "none", "none");
    }

    public static Theme theme2() {
        return new Theme(THEME_2_ID, "theme2", "none", "none");
    }

    public static Theme noReservationTheme() {
        return new Theme(NO_RESERVATION_THEME_ID, "no_reservation_theme", "none", "none");
    }

    public static Theme newTheme() {
        return new Theme(INITIAL_THEME_SIZE + 1L, "theme4", "none", "none");
    }
}
