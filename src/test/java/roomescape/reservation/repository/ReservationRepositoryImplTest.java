package roomescape.reservation.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import roomescape.reservation.database.ReservationRepositoryImpl;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.fixture.ReservationFixture;
import roomescape.reservationTime.repository.ReservationTimeRepositoryImpl;

import java.util.List;

@JdbcTest
@Import({ReservationRepositoryImpl.class, ReservationTimeRepositoryImpl.class})
class ReservationRepositoryImplTest {

    @Autowired
    private ReservationRepositoryImpl repository;

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
            repository.add(reservation);
        }

        // when & then
        Assertions.assertThatCode(
                () -> repository.findById(Long.MAX_VALUE)
        ).isInstanceOf(IncorrectResultSizeDataAccessException.class);
    }
}
