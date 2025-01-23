package roomescape.fixture;

import static roomescape.fixture.MemberFixture.MEMBER_1_ID;
import static roomescape.fixture.ReservationTimeFixture.TIME_2_ID;
import static roomescape.fixture.ThemeFixture.THEME_2_ID;

import java.time.LocalDate;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationStatus;
import roomescape.service.dto.ReservationAdminRequest;
import roomescape.service.dto.ReservationRequest;
import roomescape.service.dto.ReservationResponse;

public abstract class ReservationFixture {

    public static final int INITIAL_RESERVATION_SIZE = 3;
    public static final long RESERVATION_1_ID = 1;
    public static final long RESERVATION_2_ID = 2;
    public static final long RESERVATION_3_ID = 3;

    public static Reservation reservation1() {
        return new Reservation(
                RESERVATION_1_ID,
                MemberFixture.member1(),
                ReservationSlotFixture.slot1(),
                ReservationStatus.RESERVED
        );
    }

    public static Reservation reservation2() {
        return new Reservation(
                RESERVATION_2_ID,
                MemberFixture.member2(),
                ReservationSlotFixture.slot2(),
                ReservationStatus.RESERVED
        );
    }

    public static Reservation reservation3() {
        return new Reservation(
                RESERVATION_3_ID,
                MemberFixture.member3(),
                ReservationSlotFixture.slot3(),
                ReservationStatus.RESERVED
        );
    }

    public static ReservationRequest newRequest() {
        return new ReservationRequest(
                LocalDate.of(2100, 12, 1),
                TIME_2_ID,
                THEME_2_ID,
                ReservationStatus.RESERVED.getViewName()
        );
    }

    public static ReservationAdminRequest newAdminRequest() {
        return new ReservationAdminRequest(
                LocalDate.of(2100, 12, 1),
                TIME_2_ID,
                THEME_2_ID,
                MEMBER_1_ID,
                ReservationStatus.RESERVED.getViewName()
        );
    }

    public static ReservationRequest pastRequest() {
        return new ReservationRequest(
                LocalDate.now().minusDays(1),
                TIME_2_ID,
                THEME_2_ID,
                ReservationStatus.RESERVED.getViewName()
        );
    }

    public static ReservationResponse newResponse(ReservationStatus status) {
        return new ReservationResponse(newReservation(status));
    }

    public static Reservation newReservation(ReservationStatus status) {
        return new Reservation(
                INITIAL_RESERVATION_SIZE + 1L,
                MemberFixture.member1(),
                ReservationSlotFixture.newSlot(),
                status
        );
    }
}
