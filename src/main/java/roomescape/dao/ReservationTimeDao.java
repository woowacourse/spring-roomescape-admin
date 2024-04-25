package roomescape.dao;

import java.sql.PreparedStatement;
import java.sql.Time;
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
        String insertSQL = "insert into reservation_time (start_at) values (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, new String[]{"id"});
            preparedStatement.setTime(1, Time.valueOf(time.getStartAt()));
            return preparedStatement;
        }, keyHolder);

        return new ReservationTime(keyHolder.getKey().longValue(), time.getStartAt());
    }
}
