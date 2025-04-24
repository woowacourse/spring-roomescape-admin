package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.ReservationTime;
import roomescape.fixture.TextFixture;

@JdbcTest
class ReservationTimeRepositoryImplTest {

    private ReservationTimeRepository reservationTimeRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        reservationTimeRepository = new ReservationTimeRepositoryImpl(jdbcTemplate);

        jdbcTemplate.execute("DROP TABLE reservation IF EXISTS");
        jdbcTemplate.execute("DROP TABLE reservation_time IF EXISTS");

        jdbcTemplate.execute("CREATE TABLE reservation(" +
                "id SERIAL, name VARCHAR(255), date VARCHAR(255), time_id BIGINT)");
        jdbcTemplate.execute("CREATE TABLE reservation_time(" +
                "id SERIAL, start_at VARCHAR(255))");

        jdbcTemplate.update("insert into reservation_time(start_at) VALUES (?)",
                TextFixture.makeNowTime());

        jdbcTemplate.update("insert into reservation(name, date, time_id) VALUES (?,?,?)",
                "mint", TextFixture.makeTodayMessage(), "1");
    }

    @Test
    void insert() {
        reservationTimeRepository.insert(LocalTime.now());
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();

        assertThat(reservationTimes.size()).isEqualTo(2);
    }

    @Test
    void findAll() {
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();

        assertThat(reservationTimes.size()).isEqualTo(1);
    }

    @Test
    void delete() {
        reservationTimeRepository.delete(1);
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();

        assertThat(reservationTimes.size()).isEqualTo(0);
    }
}
