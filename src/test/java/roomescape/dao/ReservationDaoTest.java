package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import roomescape.domain.Reservation;
import roomescape.domain.Reservations;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationDaoTest {

    private static final String TEST_NAME = "TestName";
    private static final LocalDateTime TEST_DATE_TIME = LocalDateTime.MAX;

    @Autowired
    private ReservationDao reservationDao;

    private static Reservation createTestReservation() {
        return new Reservation(null, TEST_NAME, TEST_DATE_TIME);
    }

    @Test
    void 예약_목록_전체를_조회해_반환한다() {
        // given
        Reservation firstReservation = reservationDao.save(createTestReservation());
        Reservation secondReservation = reservationDao.save(createTestReservation());

        // when
        Reservations result = reservationDao.findAll();

        // then
        assertThat(result.getReservations())
                .containsExactlyElementsOf(List.of(
                        firstReservation,
                        secondReservation
                ));
    }

    @Test
    void 예약_정보를_저장해_ID가_할당된_예약_정보를_반환한다() {
        // given
        Reservation reservation = createTestReservation();

        // when
        Reservation saved = reservationDao.save(reservation);
        int size = reservationDao.findAll().getReservations().size();

        // then
        assertThat(saved.getId()).isEqualTo(1L);
        assertThat(saved.getName()).isEqualTo(TEST_NAME);
        assertThat(saved.getDateTime()).isEqualTo(TEST_DATE_TIME);
        assertThat(size).isEqualTo(1);
    }

    @Test
    void 예약_정보를_삭제_내용이_있는_경우_TRUE를_반환한다() {
        // given
        Reservation saved = reservationDao.save(createTestReservation());

        // when
        boolean result = reservationDao.deleteById(saved.getId());

        // then
        assertThat(result).isTrue();
    }

    @Test
    void 예약_정보_삭제_내역이_없는_경우_FALSE를_반환한다() {
        // when
        boolean result = reservationDao.deleteById(1L);

        // then
        assertThat(result).isFalse();
    }
}
