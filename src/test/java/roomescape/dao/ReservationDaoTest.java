package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.Reservation;
import roomescape.domain.Reservations;

@SpringBootTest(
        webEnvironment = WebEnvironment.DEFINED_PORT,
        properties = "spring.datasource.url=jdbc:h2:mem:testdb"
)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationDaoTest {

    private static final String TEST_NAME = "TestName";
    private static final LocalDateTime TEST_DATE_TIME = LocalDateTime.MAX;

    @Autowired
    private ReservationDao reservationDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void 예약_목록_전체를_조회해_반환한다() {
        // given
        Reservation firstReservation = reservationDao.save(createTestReservation());
        Reservation secondReservation = reservationDao.save(createTestReservation());

        // when
        Reservations findReservations = reservationDao.findAll();
        Integer count = getReservationCount();

        // then
        assertThat(findReservations.getReservations())
                .contains(firstReservation, secondReservation);

        assertThat(count)
                .isEqualTo(findReservations.getReservations().size());
    }

    @Test
    void 예약_정보를_저장해_ID가_할당된_예약_정보를_반환한다() {
        // given
        Reservation reservation = createTestReservation();

        // when
        Reservation saved = reservationDao.save(reservation);
        Boolean exists = isReservationExists();

        // then
        assertThat(saved.getId()).isEqualTo(1L);
        assertThat(saved.getName()).isEqualTo(TEST_NAME);
        assertThat(saved.getDateTime()).isEqualTo(TEST_DATE_TIME);
        assertThat(exists).isTrue();
    }

    @Test
    void 예약_정보를_삭제_내용이_있는_경우_TRUE를_반환한다() {
        // given
        Reservation saved = reservationDao.save(createTestReservation());

        // when
        Boolean beforeExists = isReservationExists();
        boolean result = reservationDao.deleteById(saved.getId());
        Boolean afterExists = isReservationExists();

        // then
        assertThat(result).isTrue();
        assertThat(beforeExists).isNotEqualTo(afterExists);
    }

    private Reservation createTestReservation() {
        return new Reservation(null, TEST_NAME, TEST_DATE_TIME);
    }

    @Test
    void 예약_정보_삭제_내역이_없는_경우_FALSE를_반환한다() {
        // when
        boolean result = reservationDao.deleteById(1L);
        Boolean exists = isReservationExists();

        // then
        assertThat(result).isFalse();
        assertThat(exists).isFalse();
    }

    private Integer getReservationCount() {
        return jdbcTemplate.queryForObject(
                "select count(*) from reservation",
                Integer.class
        );
    }

    private Boolean isReservationExists() {
        return jdbcTemplate.queryForObject(
                "SELECT EXISTS(SELECT 1 FROM reservation WHERE id = ?)",
                Boolean.class,
                1L
        );
    }
}
