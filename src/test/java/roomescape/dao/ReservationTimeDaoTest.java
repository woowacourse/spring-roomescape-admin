package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.ReservationTime;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeDaoTest {

    @Autowired
    private ReservationTimeDao reservationTimeDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void 예약_시간_전체를_조회해_반환한다() {
        // given
        ReservationTime firstReservationTime = reservationTimeDao.save(createTestReservationTime());
        ReservationTime secondReservationTime = reservationTimeDao.save(createTestReservationTime());

        // when
        List<ReservationTime> findReservationTimes = reservationTimeDao.findAll();
        Integer count = getReservationTimeCount();

        // then
        assertAll(
                () -> assertThat(findReservationTimes)
                        .contains(firstReservationTime, secondReservationTime),
                () -> {
                    assertThat(findReservationTimes).isNotNull();
                    assertThat(count).isEqualTo(findReservationTimes.size());
                }
        );
    }

    @Test
    void 예약_시간을_조회해_반환한다() {
        // given
        ReservationTime saved = reservationTimeDao.save(createTestReservationTime());

        // when
        ReservationTime result = reservationTimeDao.findById(1L);

        // then
        assertThat(result).isEqualTo(saved);
    }

    @Test
    void 예약_시간_정보를_저장해_ID가_할당된_예약_시간_정보를_반환한다() {
        // given
        ReservationTime reservationTime = createTestReservationTime();

        // when
        ReservationTime saved = reservationTimeDao.save(reservationTime);
        Boolean exists = isReservationTimeExists();

        // then
        assertAll(
                () -> assertThat(saved.getId()).isEqualTo(1L),
                () -> assertThat(saved.getTime()).isEqualTo(LocalTime.MIDNIGHT),
                () -> assertThat(exists).isTrue()
        );
    }

    @Test
    void 예약_시간을_삭제한_데이터가_있는_경우_TRUE를_반환한다() {
        // given
        ReservationTime saved = reservationTimeDao.save(createTestReservationTime());

        // when
        Boolean beforeExists = isReservationTimeExists();
        boolean result = reservationTimeDao.deleteById(saved.getId());
        Boolean afterExists = isReservationTimeExists();

        // then
        assertAll(
                () -> assertThat(result).isTrue(),
                () -> assertThat(beforeExists).isNotEqualTo(afterExists)
        );
    }

    @Test
    void 예약_시간을_삭제한_데이터가_없는_경우_FALSE를_반환한다() {
        // when
        boolean result = reservationTimeDao.deleteById(1L);
        Boolean exists = isReservationTimeExists();

        // then
        assertAll(
                () -> assertThat(result).isFalse(),
                () -> assertThat(exists).isFalse()
        );
    }

    private ReservationTime createTestReservationTime() {
        return new ReservationTime(null, LocalTime.MIDNIGHT);
    }

    private Integer getReservationTimeCount() {
        return jdbcTemplate.queryForObject(
                "select count(*) from reservation_time",
                Integer.class
        );
    }

    private Boolean isReservationTimeExists() {
        return jdbcTemplate.queryForObject(
                "SELECT EXISTS(SELECT 1 FROM reservation_time WHERE id = ?)",
                Boolean.class,
                1L
        );
    }
}
