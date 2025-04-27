package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.fixture.TextFixture;

@JdbcTest
class ReservationRepositoryImplTest {

    private ReservationRepository reservationRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        reservationRepository = new ReservationRepositoryImpl(jdbcTemplate);

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
    void findAll() {
        List<Reservation> reservations = reservationRepository.findAll();
        assertThat(reservations.size()).isEqualTo(1);
    }

    @Test
    void insert() {
        LocalDate now = LocalDate.now();
        ReservationTime reservationTime = new ReservationTime(1L, null);
        Reservation reservation = new Reservation(null, "밍트", now, reservationTime);
        reservationRepository.insert(reservation);

        List<Reservation> reservations = reservationRepository.findAll();
        assertThat(reservations.size()).isEqualTo(2);
    }

    @Test
    void delete() {
        reservationRepository.delete(1);

        List<Reservation> reservations = reservationRepository.findAll();
        assertThat(reservations).isEmpty();
    }
}
