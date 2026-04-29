package roomescape.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import roomescape.domain.ReservationTime;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class JdbcTemplateReservationTimeRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private JdbcTemplateReservationTimeRepository repository;

    @BeforeEach
    void setUp() {
        repository = new JdbcTemplateReservationTimeRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("ReservationTime 데이터를 데이터베이스에 저장한다.")
    public void save() {
        // given
        ReservationTime reservationTime = new ReservationTime(LocalTime.of(10, 30));

        // when
        ReservationTime saved = repository.save(reservationTime);

        // then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getStartAt()).isEqualTo(reservationTime.getStartAt());
    }

    @Test
    @DisplayName("특정 id의 ReservationTime 데이터를 조회한다.")
    public void findById() {
        // given
        ReservationTime reservationTime = createReservationTime(LocalTime.of(15, 40));

        // when
        Optional<ReservationTime> timeOptional = repository.findById(reservationTime.getId());

        // then
        assertThat(timeOptional).isPresent();
    }

    @Test
    @DisplayName("저장된 모든 ReservationTime 데이터를 조회한다.")
    public void findAll() {
        // given
        createReservationTime(LocalTime.of(15, 40));
        createReservationTime(LocalTime.of(16, 10));
        createReservationTime(LocalTime.of(17, 30));

        // when
        List<ReservationTime> reservationTimes = repository.findAll();

        // then
        assertThat(reservationTimes).hasSize(3);
    }

    @Test
    @DisplayName("특정 id의 ReservationTime을 삭제한다.")
    public void delete() {
        // given
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            String sql = "INSERT INTO reservation_time (start_at) VALUES (?)";
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, "15:40");
            return ps;
        }, keyHolder);

        // when
        long id = keyHolder.getKey().longValue();
        repository.delete(id);

        // then
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM reservation_time WHERE id = ?",
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
