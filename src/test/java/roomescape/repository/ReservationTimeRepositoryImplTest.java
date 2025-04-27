package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import roomescape.domain.ReservationTime;
import roomescape.fixture.TextFixture;

class ReservationTimeRepositoryImplTest {

    private EmbeddedDatabase database;
    private JdbcTemplate jdbcTemplate;
    private ReservationTimeRepository reservationTimeRepository;

    @BeforeEach
    void setUp() {
        database = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
        jdbcTemplate = new JdbcTemplate(database);
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
    void findAll() {
        // given

        // when
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();

        // then
        assertThat(reservationTimes.size()).isEqualTo(1);
    }

    @Test
    void save() {
        //given

        // when
        reservationTimeRepository.save(TextFixture.makeReservationTime(1L));

        // then
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();
        assertThat(reservationTimes.size()).isEqualTo(2);
    }

    @Test
    void delete() {
        // given

        // when
        reservationTimeRepository.delete(1);

        // then
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();
        assertThat(reservationTimes).isEmpty();
    }
}
