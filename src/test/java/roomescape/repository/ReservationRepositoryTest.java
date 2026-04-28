package roomescape.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
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

    @Test
    @DisplayName("찾기를 하면 모든 결과물을 반환한다")
    void findAll_success() {
        Reservation reservation1 = Reservation.constructWithNoId(TESTER_NAME, TEST_DATE, TEST_TIME);
        Reservation reservation2 = Reservation.constructWithNoId(TESTER_NAME, TEST_DATE, TEST_TIME);
        Reservation reservation3 = Reservation.constructWithNoId(TESTER_NAME, TEST_DATE, TEST_TIME);
        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);
        reservationRepository.save(reservation3);

        List<Reservation> result = reservationRepository.findAll();

        Assertions.assertEquals(3, result.size());
    }

    @Test
    @DisplayName("찾기는 비어 있어도 오류가 발생시키지 않고, 빈 리스트를 반환한다.")
    void findAll_success_when_repository_is_empty() {
        List<Reservation> result = reservationRepository.findAll();

        assertTrue(result.isEmpty());
    }
}
