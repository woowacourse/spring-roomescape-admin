package roomescape.repository;

import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReservationTime> getAll() {
        return jdbcTemplate.query(
            "SELECT id, start_at FROM reservation_time",
            reservationTimeRowMapper);
    }

    public Optional<ReservationTime> findById(long id) {
        List<ReservationTime> reservationTimes = jdbcTemplate.query(
            "SELECT id, start_at FROM reservation_time WHERE id = ?",
            reservationTimeRowMapper,
            id);
        return reservationTimes.stream().findFirst();
    }

    private final RowMapper<ReservationTime> reservationTimeRowMapper = (rs, rowNum) ->
        new ReservationTime(
            rs.getLong("id"),
            rs.getTime("start_at").toLocalTime()
        );

    public ReservationTime save(ReservationTime reservationTime) {
        final String sql = "INSERT INTO reservation_time (start_at) VALUES (?)";

        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setTime(1, Time.valueOf(reservationTime.startAt()));
            return ps;
        }, keyHolder);

        return reservationTime.withId(keyHolder.getKey().longValue());
    }

    public void deleteById(long id) {
        int update = jdbcTemplate.update("DELETE FROM reservation_time WHERE id = ?", id);
        if (update == 0) {
            throw new NoSuchElementException("존재하지 않는 예약 시간 아이디 입니다. reservationTimeId: " + id);
        }
    }
}
