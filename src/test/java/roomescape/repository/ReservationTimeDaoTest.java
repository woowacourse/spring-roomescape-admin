package roomescape.repository;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.model.ReservationTime;
import roomescape.repository.dto.ReservationTimeSaveDto;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ReservationTimeDaoTest {

    @LocalServerPort
    int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ReservationTimeDao reservationTimeDao;

    @BeforeEach
    void init() {
        RestAssured.port = port;
        jdbcTemplate.update("INSERT INTO reservation_times (start_at) VALUES (?), (?)", "08:00", "07:00");
    }

    @DisplayName("예약 시간 저장")
    @Test
    void save() {
        final ReservationTimeSaveDto reservationTimeSaveDto = new ReservationTimeSaveDto("10:00");
        final ReservationTime savedReservationTime = reservationTimeDao.save(reservationTimeSaveDto);
        assertThat(savedReservationTime.getId()).isEqualTo(3L);
        assertThat(savedReservationTime.getStartAt()).isEqualTo(LocalTime.parse("10:00"));
    }

    @DisplayName("존재하는 예약 시간 조회")
    @Test
    void findExistById() {
        final ReservationTime reservationTime = reservationTimeDao.findById(1L).orElseThrow();
        assertThat(reservationTime.getId()).isEqualTo(1L);
        assertThat(reservationTime.getStartAt()).isEqualTo(LocalTime.parse("08:00"));
    }

    @DisplayName("존재하지 않는 예약 시간 조회")
    @Test
    void findEmptyById() {
        final Optional<ReservationTime> reservationTime = reservationTimeDao.findById(4L);
        assertTrue(reservationTime.isEmpty());
    }

    @DisplayName("예약 시간 목록 조회")
    @Test
    void findAll() {
        final List<ReservationTime> reservationTimes = reservationTimeDao.findAll();
        assertThat(reservationTimes.size()).isEqualTo(2);
    }

    @DisplayName("존재하는 예약 시간 삭제")
    @Test
    void deleteExistById() {
        final boolean isDeleted = reservationTimeDao.deleteById(1L);
        assertTrue(isDeleted);
    }

    @DisplayName("존재하지 않는 예약 시간 삭제")
    @Test
    void deleteEmptyById() {
        final boolean isDeleted = reservationTimeDao.deleteById(100L);
        assertFalse(isDeleted);
    }
}
