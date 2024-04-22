package roomescape.reservation.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Time;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import roomescape.reservation.domain.ReservationTime;

@Component
public class ReservationTimeDao implements ReservationTimeRepository {
    private final JdbcTemplate jdbcTemplate;
    private final KeyHolder keyHolder = new GeneratedKeyHolder();

    public ReservationTimeDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ReservationTime save(final ReservationTime reservationTime) {
        String sql = "INSERT INTO reservation_time (start_at) values (?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setTime(1, Time.valueOf(reservationTime.getStartAt()));
            return preparedStatement;
        }, keyHolder);
        return new ReservationTime(keyHolder.getKey().longValue(), reservationTime.getStartAt());
    }

    @Override
    public List<ReservationTime> findAll() {
        String sql = "SELECT id, start_at FROM reservation_time";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            return new ReservationTime(
                    resultSet.getLong("id"),
                    resultSet.getTime("start_at").toLocalTime()
            );
        });
    }

    @Override
    public boolean delete(final long timeId) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        int deleteId = jdbcTemplate.update(sql, timeId);
        return deleteId != 0;
    }
}
