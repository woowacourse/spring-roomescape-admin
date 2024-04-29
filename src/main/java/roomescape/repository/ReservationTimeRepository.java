package roomescape.repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.model.ReservationTimeDto;

@Repository
public class ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<ReservationTimeDto> timeRowMapper = (resultSet, rowNum) ->
            new ReservationTimeDto(
                    resultSet.getLong("id"),
                    resultSet.getString("start_at")
            );

    public List<ReservationTimeDto> findAllReservationTimes() {
        String sqlToFindAll = "SELECT id, start_at FROM reservation_time";
        return jdbcTemplate.query(sqlToFindAll, timeRowMapper);
    }

    public ReservationTimeDto findReservationTimeById(final Long id) {
        String sqlToFind = "SELECT id, start_at FROM reservation_time WHERE id = ?";
        return jdbcTemplate.queryForObject(
                sqlToFind,
                timeRowMapper,
                id);
    }

    public ReservationTimeDto createReservationTime(ReservationTimeDto reservationTimeRequest) {
        String sqlToInsert = "INSERT INTO reservation_time (start_at) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    sqlToInsert,
                    new String[]{"id"});
            preparedStatement.setString(1, reservationTimeRequest.startAt());
            return preparedStatement;
        }, keyHolder);
        return findReservationTimeById(Objects.requireNonNull(keyHolder.getKey())
                .longValue());
    }

    public void deleteReservationTime(Long id) {
        String sqlToDelete = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sqlToDelete, id);
    }

}
