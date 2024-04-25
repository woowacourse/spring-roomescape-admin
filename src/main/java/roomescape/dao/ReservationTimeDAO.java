package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationTimeDAO {
    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeDAO(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationTime insert(final ReservationTime reservationTime) {
        final String sql = "INSERT INTO reservation_time (start_at) VALUES (?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        final LocalTime startAt = reservationTime.getStartAt();

        jdbcTemplate.update(con -> {
            final PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setTime(1, Time.valueOf(startAt));
            return preparedStatement;
        }, keyHolder);

        final long id = keyHolder.getKey().longValue();
        return new ReservationTime(id, startAt);
    }

    public ReservationTime findById(final Long id) {
        final String sql = "SELECT * FROM reservation_time WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, (resultSet, rowNum) ->
                new ReservationTime(
                        resultSet.getLong("id"),
                        resultSet.getTime("start_at").toLocalTime()
                ), id);
    }

    public List<ReservationTime> selectAll() {
        final String sql = "SELECT * FROM reservation_time";

        return jdbcTemplate.query(sql, (resultSet, rowNum) -> new ReservationTime(
                resultSet.getLong("id"),
                resultSet.getTime("start_at").toLocalTime()
        ));
    }
}
