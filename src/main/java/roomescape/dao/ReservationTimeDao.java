package roomescape.dao;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationTimeDao {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<ReservationTime> rowMapper = (resultSet, rowNum) -> new ReservationTime(
            resultSet.getLong("id"),
            LocalTime.parse(resultSet.getString("start_at"))
    );

    public ReservationTimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationTime save(ReservationTime reservationTime) {
        String sql = "INSERT INTO reservation_time(start_at) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, reservationTime.getStartAt().toString());
            return preparedStatement;
        }, keyHolder);
        return ReservationTime.toEntity(reservationTime, keyHolder.getKey().longValue());
    }

    public List<ReservationTime> findAll() {
        String sql = "SELECT id, start_at FROM reservation_time";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public ReservationTime findById(Long id) {
        String sql = "SELECT id, start_at FROM reservation_time WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
