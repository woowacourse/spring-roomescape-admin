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
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;
import roomescape.repository.dto.ReservationSaveDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ReservationDaoTest {

    @LocalServerPort
    int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ReservationDao reservationDao;

    @BeforeEach
    void init() {
        RestAssured.port = port;
        jdbcTemplate.update("INSERT INTO reservation_times (start_at) VALUES (?), (?)", "08:00", "07:00");
        jdbcTemplate.update("INSERT INTO reservations (name, date, time_id) VALUES (?, ?, ?), (?, ?, ?)",
                "감자", "2024-07-07", 1L,
                "고구마", "2024-08-12", 2L);
    }

    @DisplayName("예약 생성")
    @Test
    void save() {
        final ReservationTime reservationTime = new ReservationTime(1L, "08:00");
        final ReservationSaveDto reservationSaveDto = new ReservationSaveDto("생강", "2025-01-01", reservationTime);
        final Reservation reservation = reservationDao.save(reservationSaveDto);
        assertAll(
                () -> assertThat(reservation.getId()).isEqualTo(3L),
                () -> assertThat(reservation.getName()).isEqualTo("생강"),
                () -> assertThat(reservation.getDate()).isEqualTo("2025-01-01"),
                () -> assertThat(reservation.getTime()).isEqualTo(reservationTime)
        );
    }

    @DisplayName("예약 목록 조회")
    @Test
    void findAll() {
        final List<Reservation> reservations = reservationDao.findAll();
        assertThat(reservations.size()).isEqualTo(2);
    }

    @DisplayName("존재하는 예약 삭제")
    @Test
    void deleteExistById() {
        final boolean isDeleted = reservationDao.deleteById(1L);
        assertTrue(isDeleted);
    }

    @DisplayName("존재하지 않는 예약 삭제")
    @Test
    void deleteEmptyById() {
        final boolean isDeleted = reservationDao.deleteById(3L);
        assertFalse(isDeleted);
    }
}
