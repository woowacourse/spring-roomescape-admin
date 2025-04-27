package roomescape.dao;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

@Repository
public class ReservationDAO {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        ReservationTime time = new ReservationTime(
                resultSet.getLong("time_id"),
                resultSet.getTime("time_value").toLocalTime()
        );
        return new Reservation(
                resultSet.getLong("reservation_id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                time
        );
    };

    public ReservationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String sql = """
                SELECT
                r.id as reservation_id,
                r.name,
                r.date,
                t.id as time_id,
                t.start_at as time_value
                FROM reservation AS r
                INNER JOIN reservation_time AS t
                ON r.time_id = t.id
                """;
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    public Reservation addAndGet(String name, LocalDate date, long timeId) {
        SimpleJdbcInsert simpleJdbcInsert = makeSimpleJdbcInsert();
        Map<String, Object> parameters = Map.of(
                "name", name,
                "date", date,
                "time_id", timeId
        );

        Number id = simpleJdbcInsert.executeAndReturnKey(parameters);
        Time startAt = jdbcTemplate.queryForObject("SELECT start_at FROM reservation_time WHERE id = ?", Time.class, timeId);

        ReservationTime timeRes = new ReservationTime(timeId, startAt.toLocalTime());
        return new Reservation(id.longValue(), name, date, timeRes);
    }

    private SimpleJdbcInsert makeSimpleJdbcInsert() {
        return new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingColumns("name", "date", "time_id")
                .usingGeneratedKeyColumns("id");
    }

    public void deleteById(Long id) {
        int rows = jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
        if (rows == 0) {
            throw new EmptyResultDataAccessException(rows);
        }
    }
}
