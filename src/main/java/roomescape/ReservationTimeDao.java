package roomescape;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationTimeDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private static final RowMapper<ReservationTime> ROW_MAPPER = (resultSet, rowNum) ->
            new ReservationTime(
                    resultSet.getLong("id"),
                    resultSet.getTime("start_at").toLocalTime()
            );

    public ReservationTimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    public ReservationTime save(ReservationTime reservationTime) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("start_at", reservationTime.getStartAt());

        Number id = simpleJdbcInsert.executeAndReturnKey(parameters);

        return new ReservationTime(
                id.longValue(),
                reservationTime.getStartAt()
        );
    }

    public List<ReservationTime> findAllReservationTimes() {
        String sql = """
                SELECT id, 
                       start_at
                FROM reservation_time""";
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    public Optional<ReservationTime> findById(Long reservationTimeId) {
        String sql = """
                SELECT id, 
                       start_at
                FROM reservation_time
                WHERE id = ?""";

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, ROW_MAPPER, reservationTimeId));
    }

    public void delete(ReservationTime reservationTime) {
        String sql = """
                DELETE FROM reservation_time
                WHERE id = ?""";

        jdbcTemplate.update(sql, reservationTime.getId());
    }
}
