package roomescape.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.ReservationTimeDto;

@Repository
public class ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    private static final RowMapper<ReservationTimeDto> timeRowMapper = (resultSet, rowNum) ->
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

    public ReservationTimeDto createReservationTime(ReservationTimeDto reservationTimeDto) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("start_at", reservationTimeDto.startAt());

        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
        return findReservationTimeById(newId.longValue());
    }

    public void deleteReservationTime(Long id) {
        String sqlToDelete = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sqlToDelete, id);
    }

}
