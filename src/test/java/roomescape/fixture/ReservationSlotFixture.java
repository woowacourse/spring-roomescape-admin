package roomescape.fixture;

import java.time.LocalDate;
import roomescape.domain.ReservationSlot;

public abstract class ReservationSlotFixture {

    public static final int INITIAL_SLOT_SIZE = 3;
    public static final long SLOT_1_ID = 1;
    public static final long SLOT_2_ID = 2;
    public static final long SLOT_3_ID = 3;

    public static ReservationSlot slot1() {
        return new ReservationSlot(
                SLOT_1_ID,
                LocalDate.of(2100, 12, 1),
                ReservationTimeFixture.time1(),
                ThemeFixture.theme1()
        );
    }

    public static ReservationSlot slot2() {
        return new ReservationSlot(
                SLOT_2_ID,
                LocalDate.of(2100, 12, 1),
                ReservationTimeFixture.time1(),
                ThemeFixture.theme2()
        );
    }

    public static ReservationSlot slot3() {
        return new ReservationSlot(
                SLOT_3_ID,
                LocalDate.of(2100, 12, 1),
                ReservationTimeFixture.time2(),
                ThemeFixture.theme1()
        );
    }

    public static ReservationSlot newSlot() {
        return new ReservationSlot(
                INITIAL_SLOT_SIZE + 1L,
                LocalDate.of(2100, 12, 1),
                ReservationTimeFixture.time2(),
                ThemeFixture.theme2()
        );
    }

    public static ReservationSlot newSlotWithoutId() {
        return new ReservationSlot(
                LocalDate.of(2100, 12, 1),
                ReservationTimeFixture.time2(),
                ThemeFixture.theme2()
        );
    }

    public static ReservationSlot newPastSlotWithoutId() {
        return new ReservationSlot(
                LocalDate.of(2000, 12, 1),
                ReservationTimeFixture.time2(),
                ThemeFixture.theme2()
        );
    }
}
