package roomescape.time.dao;

import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.time.ReservationTime;

@Repository
public class JdbcReservationTimeDao implements ReservationTimeDao {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<ReservationTime> reservationRowMapper = (resultSet, rowNum) -> {
        return new ReservationTime(
                resultSet.getLong("id"),
                resultSet.getTime("start_at").toLocalTime()
        );
    };

    public JdbcReservationTimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReservationTime> findAll() {
        String sql = "SELECT * FROM reservation_time";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    public ReservationTime save(ReservationTime reservationTime) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO reservation_time(start_at) VALUES(?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setTime(1, Time.valueOf(reservationTime.getStartAt()));
            return preparedStatement;
        }, keyHolder);
        return new ReservationTime(keyHolder.getKeyAs(Long.class), reservationTime.getStartAt());
    }

    public boolean removeById(long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        int rowNumber = jdbcTemplate.update(sql, id);
        return rowNumber == 1;
    }

    public ReservationTime getById(long id) {
        String sql = "SELECT * FROM reservation_time WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, reservationRowMapper, id);
    }
}
