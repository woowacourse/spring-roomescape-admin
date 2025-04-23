package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.ReservationTime;

@SpringBootTest(
        webEnvironment = WebEnvironment.DEFINED_PORT,
        properties = "spring.datasource.url=jdbc:h2:mem:testdb"
)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeDaoTest {

    @Autowired
    private ReservationTimeDao reservationTimeDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void 예약_시간_정보를_저장해_ID가_할당된_예약_시간_정보를_반환한다() {
        // given
        ReservationTime reservationTime = new ReservationTime(null, LocalTime.MIDNIGHT);

        // when
        ReservationTime saved = reservationTimeDao.save(reservationTime);
        Boolean exists = isReservationTimeExists();

        // then
        assertThat(saved.getId()).isEqualTo(1L);
        assertThat(saved.getTime()).isEqualTo(LocalTime.MIDNIGHT);
        assertThat(exists).isTrue();
    }

    private Boolean isReservationTimeExists() {
        return jdbcTemplate.queryForObject(
                "SELECT EXISTS(SELECT 1 FROM reservation_time WHERE id = ?)",
                Boolean.class,
                1L
        );
    }
}
