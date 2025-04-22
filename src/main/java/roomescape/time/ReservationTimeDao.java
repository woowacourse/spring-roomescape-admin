package roomescape.time;

import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class ReservationTimeDao {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<ReservationTime> reservationRowMapper = (resultSet, rowNum) -> {
        return new ReservationTime(
                resultSet.getLong("id"),
                resultSet.getTime("start_at").toLocalTime()
        );
    };

    public ReservationTimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReservationTime> findAll() {
        String sql = "select * from reservation_time";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    public ReservationTime save(ReservationTime reservationTime) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into reservation_time(start_at) values(?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setTime(1, Time.valueOf(reservationTime.getStartAt()));
            return preparedStatement;
        }, keyHolder);
        return new ReservationTime(keyHolder.getKeyAs(Long.class), reservationTime.getStartAt());
    }
}
