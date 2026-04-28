package roomescape.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;

class ReservationRepositoryTest {

    private static final long TEST_ID = 1L;
    private static final String TESTER_NAME = "라티";
    private static final String TEST_DATE = "2026-24-28";
    private static final String TEST_TIME = "18:00";

    ReservationEntityMapper mapper = new ReservationEntityMapper();
    ReservationRepository reservationRepository = new ReservationRepository(mapper);

    @Test
    @DisplayName("저장을 하고, ID가 있는 Reservation를 반환한다")
    void save_success() {
        //given
        Reservation reservation = Reservation.constructWithNoId(TESTER_NAME, TEST_DATE, TEST_TIME);

        //when
        Reservation result = reservationRepository.save(reservation);

        //then
        Assertions.assertNotNull(result.id());
    }
}
