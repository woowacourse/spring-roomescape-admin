package roomescape.dao;

import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationTimeDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationTime save(ReservationTime time) {
        String insertSQL = "insert into reservation_time (start_at) values ?";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, new String[]{"id"});
            preparedStatement.setTime(1, Time.valueOf(time.getStartAt()));
            return preparedStatement;
        }, keyHolder);

        return findById(keyHolder.getKey().longValue());
    }

    public List<ReservationTime> findAll() {
        String selectAllSQL = "select id, start_at from reservation_time";

        return jdbcTemplate.query(selectAllSQL, (resultSet, rowNum) -> new ReservationTime(
                resultSet.getLong("id"),
                resultSet.getTime("start_at").toLocalTime()
        ));
    }

    public void delete(Long reservationTimeId) {
        String deleteSQL = "delete from reservation_time where id = ?";
        jdbcTemplate.update(deleteSQL, reservationTimeId);
    }

    public ReservationTime findById(Long timeId) {
        String selectSQL = "select id, start_at from reservation_time where id = ?";

        return jdbcTemplate.queryForObject(selectSQL, (result, rowNum) -> new ReservationTime(
                result.getLong("id"),
                result.getTime("start_at").toLocalTime()
        ), timeId);
    }
}
