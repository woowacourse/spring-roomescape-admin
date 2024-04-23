package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeAddRequest;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ReservationTimeDao reservationTimeDao;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("insert into reservation_time (start_at) values('10:00')");
    }

    @AfterEach
    void setDown() {
        jdbcTemplate.update("delete from reservation_time");
    }

    @DisplayName("예약시간 모두를 불러옵니다.")
    @Test
    void should_find_all() {
        int expectedSize = 1;

        int actualSize = reservationTimeDao.findAll().size();

        assertThat(actualSize).isEqualTo(expectedSize);
    }

    @DisplayName("원하는 ID의 예약시간을 불러옵니다.")
    @Test
    void should_find_reservation_time_by_id() {
        ReservationTime expectedReservationTime = new ReservationTime(1L, LocalTime.of(10, 0));

        ReservationTime actualReservationTime = reservationTimeDao.findById(1L);

        assertThat(actualReservationTime).isEqualTo(expectedReservationTime);
    }


    @DisplayName("예약시간을 추가하고 예약시간 정보를 반환합니다.")
    @Test
    void should_get_reservation_time_after_insert() {
        ReservationTime expectedReservationTime = new ReservationTime(2L, LocalTime.of(11, 0));

        ReservationTime actualReservationTime = reservationTimeDao.insert(
                new ReservationTimeAddRequest(LocalTime.of(11, 0)));

        assertThat(actualReservationTime).isEqualTo(expectedReservationTime);
    }

    @DisplayName("원하는 ID의 예약시간을 취소하고 영향받은 row개수를 반환합니다.")
    @Test
    void should_get_affected_row_count_after_remove_by_id() {
        int expectedResult = 1;

        int actualResult = reservationTimeDao.deleteById(1L);

        assertThat(actualResult).isEqualTo(expectedResult);
    }
}
