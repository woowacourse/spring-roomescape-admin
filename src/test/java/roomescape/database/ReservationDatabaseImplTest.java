package roomescape.database;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.Reservation;
import roomescape.fixture.ReservationFixture;

import java.util.List;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationDatabaseImplTest {

    @DisplayName("존재하지 않는 예약 ID로 조회하면 예외가 발생한다.")
    @Test
    void findById_throwsExceptionByNonExistentId() {
        // given
        String dummyName1 = "kali";
        int dummyFuturePlusDay1 = 1;
        Reservation reservation1 = ReservationFixture.createFutureReservationAfterDays(dummyName1, dummyFuturePlusDay1);

        String dummyName2 = "pobi";
        int dummyFuturePlusDay2 = 2;
        Reservation reservation2 = ReservationFixture.createFutureReservationAfterDays(dummyName2, dummyFuturePlusDay2);

        List<Reservation> reservations = List.of(reservation1, reservation2);

        ReservationDatabaseImpl db = new ReservationDatabaseImpl();
        for (Reservation reservation : reservations) {
            db.add(reservation);
        }

        // when & then
        Assertions.assertThatCode(
                () -> db.findById(Long.MAX_VALUE)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
