package roomescape.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.Reservation;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static roomescape.TestFixture.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationDbRepositoryTest implements ReservationRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("TRUNCATE TABLE reservation");
    }

    @Override
    @Test
    @DisplayName("예약을 저장한다.")
    public void save() {
        // given
        Reservation reservation = MIA_RESERVATION();

        // when
        Reservation savedReservation = reservationRepository.save(reservation);

        // then
        assertThat(savedReservation.getId()).isNotNull();
    }

    @Override
    @Test
    @DisplayName("동일시간대의 예약 목록을 조회한다.")
    public void findAllByDateAndTime() {
        // given
        String insertSql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?), (?, ?, ?), (?, ?, ?)";
        jdbcTemplate.update(
                insertSql,
                USER_MIA, MIA_RESERVATION_DATE.toString(), MIA_RESERVATION_TIME.toString(),
                USER_TOMMY, MIA_RESERVATION_DATE.toString(), MIA_RESERVATION_TIME.toString()
        );

        // when
        List<Reservation> reservations = reservationRepository.findAllByDateAndTime(MIA_RESERVATION_DATE, MIA_RESERVATION_TIME);

        // then
        assertThat(reservations).hasSize(2)
                .extracting(Reservation::getName)
                .containsExactly(USER_MIA, USER_TOMMY);
    }

    @Override
    @Test
    @DisplayName("모든 예약 목록을 조회한다.")
    public void findAll() {
        // given
        String insertSql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
        jdbcTemplate.update(insertSql, USER_MIA, MIA_RESERVATION_DATE, MIA_RESERVATION_TIME);

        // when
        List<Reservation> reservations = reservationRepository.findAll();

        // then
        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(reservations.size()).isEqualTo(count);
    }

    @Override
    @Test
    @DisplayName("Id로 예약을 조회한다.")
    public void findById() {
        // given
        String insertSql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertSql, new String[]{"id"});
            ps.setString(1, USER_MIA);
            ps.setDate(2, Date.valueOf(MIA_RESERVATION_DATE));
            ps.setTime(3, Time.valueOf(MIA_RESERVATION_TIME));
            return ps;
        }, keyHolder);
        Long id = keyHolder.getKey().longValue();

        // when
        Optional<Reservation> reservation = reservationRepository.findById(id);

        // then
        assertThat(reservation).isPresent();
    }

    @Override
    @Test
    @DisplayName("Id로 예약을 삭제한다.")
    public void deleteById() {
        // given
        String insertSql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertSql, new String[]{"id"});
            ps.setString(1, USER_MIA);
            ps.setDate(2, Date.valueOf(MIA_RESERVATION_DATE));
            ps.setTime(3, Time.valueOf(MIA_RESERVATION_TIME));
            return ps;
        }, keyHolder);
        Long id = keyHolder.getKey().longValue();

        // when
        reservationRepository.deleteById(id);

        // then
        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation where id = ?", Integer.class, id);
        assertThat(count).isEqualTo(0);
    }
}
