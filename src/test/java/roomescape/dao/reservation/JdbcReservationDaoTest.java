package roomescape.dao.reservation;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import roomescape.dao.resetvationTime.JdbcReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.request.ReservationTimeCreateRequest;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class JdbcReservationDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JdbcReservationDao jdbcReservationDao;

    @Autowired
    private JdbcReservationTimeDao jdbcReservationTimeDao;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("DELETE FROM reservation");
        jdbcTemplate.update("DELETE FROM reservation_time");
    }

    @DisplayName("에약을 데이터베이스에 추가한다.")
    @Test
    void addTest() {

        // given
        Long timeId = jdbcReservationTimeDao.create(new ReservationTimeCreateRequest(LocalTime.of(10, 10)));
        jdbcReservationDao.create(new ReservationCreateRequest("체체", LocalDate.of(2024, 10, 10), timeId));

        // when
        List<Reservation> reservations = jdbcReservationDao.findAll();

        // then
        assertThat(reservations.getFirst().getName()).isEqualTo("체체");
    }

    @DisplayName("에약을 데이터베이스에서 조회한다.")
    @Test
    void findAllTest() {

        // given
        Long timeId = jdbcReservationTimeDao.create(new ReservationTimeCreateRequest(LocalTime.of(10, 10)));
        jdbcReservationDao.create(new ReservationCreateRequest("체체", LocalDate.of(2024, 10, 10), timeId));
        jdbcReservationDao.create(new ReservationCreateRequest("체체", LocalDate.of(2024, 11, 10), timeId));

        // when
        List<Reservation> reservations = jdbcReservationDao.findAll();

        // then
        assertThat(reservations.size()).isEqualTo(2);
    }

    @DisplayName("예약을 삭제한다.")
    @Test
    void deleteTest() {

        // given
        Long timeId = jdbcReservationTimeDao.create(new ReservationTimeCreateRequest(LocalTime.of(10, 10)));
        Long reservationId = jdbcReservationDao.create(
                new ReservationCreateRequest("체체", LocalDate.of(2024, 10, 10), timeId));

        // when
        jdbcReservationDao.delete(reservationId);
        List<Reservation> reservations = jdbcReservationDao.findAll();

        // then
        assertThat(reservations.size()).isEqualTo(0);
    }
}
