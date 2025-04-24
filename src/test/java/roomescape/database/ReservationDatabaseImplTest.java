package roomescape.database;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import roomescape.domain.reservation.Reservation;
import roomescape.fixture.ReservationFixture;

import java.util.List;

@JdbcTest
@Import({ReservationDatabaseImpl.class})
class ReservationDatabaseImplTest {

    @Autowired
    private ReservationDatabaseImpl db;

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

        for (Reservation reservation : reservations) {
            db.add(reservation);
        }

        // when & then
        Assertions.assertThatCode(
                () -> db.findById(Long.MAX_VALUE)
        ).isInstanceOf(IncorrectResultSizeDataAccessException.class);
    }
}
