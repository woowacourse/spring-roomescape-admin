package roomescape.repository;

import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.entity.ReservationTime;

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

    private final RowMapper<ReservationTime> reservationTimeRowMapper = (rs, rowNum) ->
        new ReservationTime(
            rs.getLong("id"),
            rs.getTime("start_at").toLocalTime()
        );

    public Long save(ReservationTime reservationTime) {
        final String sql = "INSERT INTO reservation_time (start_at) VALUES (?)";

        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setTime(1, Time.valueOf(reservationTime.getStartAt()));
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }
}
