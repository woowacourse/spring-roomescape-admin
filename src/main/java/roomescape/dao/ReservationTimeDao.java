package roomescape.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import roomescape.data.vo.ReservationTime;

@Component
public class ReservationTimeDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long save(final ReservationTime reservationTime) {
        final var sql = "INSERT INTO reservation_time(start_at) values (?)";
        final var keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement pstmt = con.prepareStatement(sql, new String[] {"id"});
            pstmt.setTime(1, java.sql.Time.valueOf(reservationTime.getStartAt()));
            return pstmt;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public List<ReservationTime> findAll() {
        final var sql = "SELECT id, start_at FROM reservation_time";
        return jdbcTemplate.query(sql, actorRowMapper());
    }

    public void delete(final long id) {
        final var sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public ReservationTime findOne(final long id) {
        final var sql = "SELECT id, start_at FROM reservation_time WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, actorRowMapper(), id);
    }

    private static RowMapper<ReservationTime> actorRowMapper() {
        return (resultSet, rowNum) -> new ReservationTime(resultSet.getLong("id"),
                resultSet.getTime("start_at").toLocalTime());
    }
}
