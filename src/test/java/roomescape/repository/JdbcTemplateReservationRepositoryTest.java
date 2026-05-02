package roomescape.repository;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;


@JdbcTest
class JdbcTemplateReservationRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private JdbcTemplateReservationRepository repository;

    @BeforeEach
    void setUp() {
        repository = new JdbcTemplateReservationRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("Reservation 데이터를 데이터베이스에 저장한다.")
    public void save() {
        // given
        LocalTime startAt = LocalTime.of(15, 40);
        ReservationTime reservationTime = createReservationTime(startAt);
        Reservation reservation = new Reservation(
                "name", LocalDate.of(2023, 8, 5), reservationTime);

        // when
        Reservation saved = repository.save(reservation);

        // then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved).extracting(
                Reservation::getName,
                Reservation::getDate,
                Reservation::getTime
        ).containsExactly(reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    @Test
    @DisplayName("저장된 모든 Reservation 데이터를 조회한다.")
    public void findAll() {
        ReservationTime reservationTime1 = createReservationTime(LocalTime.of(15, 40));
        ReservationTime reservationTime2 = createReservationTime(LocalTime.of(16, 10));
        ReservationTime reservationTime3 = createReservationTime(LocalTime.of(17, 30));
        // given
        jdbcTemplate.update(
                "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)",
                "kim", "2023-08-05", reservationTime1.getId()
        );
        jdbcTemplate.update(
                "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)",
                "lee", "2023-08-06", reservationTime2.getId()
        );
        jdbcTemplate.update(
                "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)",
                "park", "2023-08-07", reservationTime3.getId()
        );

        // when
        List<Reservation> reservations = repository.findAll();

        // then
        assertThat(reservations).hasSize(3)
                .extracting(
                        Reservation::getName,
                        Reservation::getDate,
                        reservation -> reservation.getTime().getStartAt()
                ).containsExactlyInAnyOrder(
                        tuple("kim", LocalDate.of(2023, 8, 5), LocalTime.of(15, 40)),
                        tuple("lee", LocalDate.of(2023, 8, 6), LocalTime.of(16, 10)),
                        tuple("park", LocalDate.of(2023, 8, 7), LocalTime.of(17, 30))
                );
    }

    @Test
    @DisplayName("특정 id의 Reservation을 삭제한다.")
    public void delete() {
        // given
        ReservationTime reservationTime = createReservationTime(LocalTime.of(15, 40));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, "kim");
            ps.setString(2, "2023-08-05");
            ps.setLong(3, reservationTime.getId());
            return ps;
        }, keyHolder);

        // when
        long id = keyHolder.getKey().longValue();
        repository.delete(id);

        // then
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM reservation WHERE id = ?",
                Integer.class,
                id
        );

        assertThat(count).isZero();
    }

    private ReservationTime createReservationTime(LocalTime startAt) {
        String sql = "insert into reservation_time(start_at) values(?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, startAt.toString());
            return ps;
        }, keyHolder);

        return new ReservationTime(keyHolder.getKey().longValue(), startAt);
    }
}
