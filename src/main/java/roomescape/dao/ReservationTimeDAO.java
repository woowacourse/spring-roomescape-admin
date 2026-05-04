package roomescape.dao;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import roomescape.domain.ReservationTime;
import roomescape.dto.TimeCreateResponse;

@Repository
public class ReservationTimeDAO {
    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<TimeCreateResponse> findAllTimes() {
        String sql = "select id as time_id, start_at as time_value from reservation_time";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new TimeCreateResponse(
                        resultSet.getLong("time_id"),
                        resultSet.getString("time_value")
                ));
    }

    public Long insertWithKeyHolder(ReservationTime reservationTime) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into reservation_time (start_at) values (?)",
                    new String[]{"id"});
            ps.setString(1, reservationTime.getStartAt());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public ReservationTime findTimeById(Long timeId) {
        String sql = "select id, start_at from reservation_time where id = ?";
        return jdbcTemplate.queryForObject(
                sql,
                (resultSet, rowNum) -> new ReservationTime(
                        resultSet.getString("start_at")
                ),
                timeId
        );
    }

    public int deleteTime(Long id) {
        String sql = "delete from reservation_time where id = ?";
        return jdbcTemplate.update(sql, Long.valueOf(id));
    }

}
