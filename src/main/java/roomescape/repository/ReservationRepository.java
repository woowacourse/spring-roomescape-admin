package roomescape.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.ReservationDto;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    private static final RowMapper<ReservationDto> reservationRowMapper = (resultSet, rowNum) ->
            new ReservationDto(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("date"),
                    resultSet.getLong("time_id"),
                    resultSet.getString("start_at")
            );

    public List<ReservationDto> findAllReservations() {
        String sqlToFind = "SELECT"
                + " r.id as reservation_id,"
                + " r.name,"
                + " r.date,"
                + " t.id AS time_id,"
                + " t.start_at AS time_value"
                + " FROM reservation AS r"
                + " INNER JOIN reservation_time AS t"
                + " ON r.time_id = t.id";

        return jdbcTemplate.query(
                sqlToFind,
                reservationRowMapper);
    }

    public ReservationDto findReservationById(final Long id) {
        String sqlToFind = "SELECT"
                + " r.id AS reservation_id,"
                + " r.name,"
                + " r.date,"
                + " t.id AS time_id,"
                + " t.start_at AS time_value"
                + " FROM reservation AS r"
                + " INNER JOIN reservation_time AS t"
                + " ON r.time_id = t.id"
                + " WHERE r.id = ?";

        return jdbcTemplate.queryForObject(
                sqlToFind,
                reservationRowMapper, id);
    }

    public ReservationDto createReservation(ReservationDto reservationDto) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", reservationDto.name());
        parameters.put("date", reservationDto.date());
        parameters.put("time_id", reservationDto.timeId());

        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
        return findReservationById(newId.longValue());
    }

    public void deleteReservation(Long id) {
        String sqlToDelete = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sqlToDelete, id);
    }

}
