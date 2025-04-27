package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationDaoTest {

    private static final String TEST_NAME = "TestName";
    private static final LocalDate TEST_DATE = LocalDate.MAX;
    private static final ReservationTime TEST_RESERVATION_TIME = new ReservationTime(1L, LocalTime.MIDNIGHT);

    @Autowired
    private ReservationDao reservationDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update(
                "insert into reservation_time (id, start_at) values (?, ?)",
                TEST_RESERVATION_TIME.getId(),
                TEST_RESERVATION_TIME.getTime()
        );
    }

    @Test
    void 예약_전체를_조회해_반환한다() {
        // given
        Reservation testReservation = reservationDao.save(createTestReservation());

        // when
        List<Reservation> findReservations = reservationDao.findAll();
        Integer count = getReservationCount();

        // then
        assertAll(
                () -> assertThat(findReservations).contains(testReservation),
                () -> {
                    assertNotNull(findReservations);
                    assertThat(count).isEqualTo(findReservations.size());
                }
        );
    }

    @Test
    void 예약을_저장해_ID가_할당된_예약을_반환한다() {
        // given
        Reservation reservation = createTestReservation();

        // when
        Reservation savedReservation = reservationDao.save(reservation);
        Boolean exists = isReservationExists();

        // then
        assertAll(
                () -> assertThat(savedReservation.getId()).isEqualTo(1L),
                () -> assertThat(savedReservation.getName()).isEqualTo(TEST_NAME),
                () -> assertThat(savedReservation.getDate()).isEqualTo(TEST_DATE),
                () -> assertThat(savedReservation.getTime()).isEqualTo(TEST_RESERVATION_TIME),
                () -> assertThat(exists).isTrue()
        );
    }

    @Test
    void 예약을_삭제한_데이터가_있는_경우_TRUE를_반환한다() {
        // given
        Reservation saved = reservationDao.save(createTestReservation());

        // when
        Boolean beforeExists = isReservationExists();
        boolean result = reservationDao.deleteById(saved.getId());
        Boolean afterExists = isReservationExists();

        // then
        assertAll(
                () -> assertThat(result).isTrue(),
                () -> assertThat(beforeExists).isNotEqualTo(afterExists)
        );
    }

    @Test
    void 예약을_삭제한_데이터가_없는_경우_FALSE를_반환한다() {
        // when
        boolean result = reservationDao.deleteById(1L);
        Boolean exists = isReservationExists();

        // then
        assertAll(
                () -> assertThat(result).isFalse(),
                () -> assertThat(exists).isFalse()
        );
    }

    private Reservation createTestReservation() {
        return new Reservation(null, TEST_NAME, TEST_DATE, TEST_RESERVATION_TIME);
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
