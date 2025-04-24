package roomescape.repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.model.ReservationTime;

@Repository
public class ReservationTimeRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationTime addTime(String startAt) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into reservation_time (start_at) values (?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, startAt);
            return ps;
        }, keyHolder);
        return new ReservationTime(Objects.requireNonNull(keyHolder.getKey())
                .longValue(), startAt);
    }

    public List<ReservationTime> getAllTime() {
        String sql = "select * from reservation_time";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new ReservationTime(
                rs.getLong("id"),
                rs.getString("start_at")
        ));
    }

    public Integer deleteTime(Long id) {
        return jdbcTemplate.update("delete from reservation_time where id = ?", id);
    }

    public ReservationTime getReservationTimeById(Long id) {
        String sql = "select start_at from reservation_time where id = ?";
        String start_at = jdbcTemplate.queryForObject(sql, String.class, id);
        return new ReservationTime(id, start_at);
    }
}
