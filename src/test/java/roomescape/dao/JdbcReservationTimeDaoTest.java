package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.model.ReservationTime;

class JdbcReservationTimeDaoTest {

    private static JdbcReservationTimeDao dao;
    private static JdbcTemplate jdbcTemplate;

    @BeforeAll
    static void setUpAll() {
        DataSource dataSource = DataSourceBuilder.create().url("jdbc:h2:mem:testDB").username("sa").build();
        jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.execute("CREATE TABLE reservation_time("
                + "id BIGINT AUTO_INCREMENT PRIMARY KEY,"
                + "start_at TIME NOT NULL)");

        dao = new JdbcReservationTimeDao(jdbcTemplate);
    }

    @BeforeEach
    void setUpDate() {
        jdbcTemplate.execute("TRUNCATE TABLE reservation_time");
    }

    @Test
    void 예약시간_저장후_조회() {
        ReservationTime reservationTime = new ReservationTime(null, LocalTime.of(10,0));
        ReservationTime saved = dao.save(reservationTime);
        List<ReservationTime> all = dao.findAll();

        assertThat(all).hasSize(1);
        assertThat(all.getFirst().getId()).isEqualTo(saved.getId());
        assertThat(all.getFirst().getStartAt()).isEqualTo(saved.getStartAt());
    }

    @Test
    void 예약시간_삭제() {
        ReservationTime reservationTime = new ReservationTime(null, LocalTime.of(10,0));
        ReservationTime saved = dao.save(reservationTime);
        boolean isDeleted = dao.deleteById(saved.getId());

        List<ReservationTime> all = dao.findAll();
        assertThat(isDeleted).isTrue();
        assertThat(all).isEmpty();
    }

    @Test
    void id로_예약시간_조회() {
        ReservationTime reservationTime = new ReservationTime(null, LocalTime.of(10,0));
        ReservationTime saved = dao.save(reservationTime);

        ReservationTime foundReservationTime = dao.findById(saved.getId());
        assertThat(foundReservationTime.getId()).isEqualTo(saved.getId());
        assertThat(foundReservationTime.getStartAt()).isEqualTo(saved.getStartAt());
    }
}
