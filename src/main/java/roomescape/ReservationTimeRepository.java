package roomescape;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<ReservationTime> timeRowMapper = (resultSet, rowNum) ->
            new ReservationTime(
                    resultSet.getLong("id"),
                    resultSet.getString("start_at")
            );

    public List<ReservationTime> findAllReservationTimes() {
        String sqlToFindAll = "SELECT id, start_at FROM reservation_time";
        return jdbcTemplate.query(sqlToFindAll, timeRowMapper);
    }

    public ReservationTime findReservationTimeById(final Long id) {
        String sqlToFind = "SELECT id, start_at FROM reservation_time WHERE id = ?";
        return jdbcTemplate.queryForObject(
                sqlToFind,
                timeRowMapper,
                id);
    }

    public ReservationTime createReservationTime(ReservationTime reservationTime) {
        String sqlToInsert = "INSERT INTO reservation_time (start_at) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    sqlToInsert,
                    new String[]{"id"});
            preparedStatement.setString(1, reservationTime.startAt());
            return preparedStatement;
        }, keyHolder);
        return reservationTime.toIdAssigned(Objects.requireNonNull(keyHolder.getKey())
                .longValue());
    }

    public void deleteReservationTime(Long id) {
        String sqlToDelete = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sqlToDelete, id);
    }

}
