package roomescape.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.entity.ReservationEntity;
import roomescape.entity.ReservationTimeEntity;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class JdbcReservationDaoTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ReservationDao reservationDao;

    @DisplayName("생성 테스트")
    @Test
    void createTest() {
        // given
        jdbcTemplate.update("INSERT INTO reservation_time(start_at) VALUES (?)", "10:00");

        ReservationTimeEntity time = new ReservationTimeEntity(1L, LocalTime.of(10, 0));
        ReservationEntity reservation = new ReservationEntity(1L, "test", LocalDate.of(2025, 1, 2), time);

        // when
        reservationDao.save(reservation);

        // then
        assertThat(reservationDao.findAll()).hasSize(1);
     }

    @DisplayName("삭제 테스트")
    @Test
    void deleteTest() {
        // given
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)", "test", "2025-01-01", 1);

        // when
        reservationDao.deleteById(1L);

        // then
        assertThat(reservationDao.findAll()).hasSize(0);
    }
}
