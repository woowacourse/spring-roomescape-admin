package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Reservation> findAll() {
        final String sql = """
                SELECT
                    r.id as reservation_id,
                    r.name,
                    r.date,
                    t.id as time_id,
                    t.start_at as time_value
                FROM reservation as r
                inner join reservation_time as t
                on r.time_id = t.id
                """;
        List<Reservation> query = jdbcTemplate.query(sql, (resultSet, rowNumber) -> {
            long id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            LocalDate date = LocalDate.parse(resultSet.getString("date"));
            long timeId = resultSet.getLong("time_id");
            LocalTime time = LocalTime.parse(resultSet.getString("time_value"));
            ReservationTime reservationTime = new ReservationTime(timeId, time);
            return new Reservation(id, name, date, reservationTime);
        });
        return query;
    }

    @Override
    public Reservation insert(final String name, final LocalDate date, final long timeId) {
        long reservationId = insertReservation(name, date, timeId);
        ReservationTime reservationTime = findReservationTime(timeId);
        return new Reservation(reservationId, name, date, reservationTime);
    }

    private ReservationTime findReservationTime(final long timeId) {
        final String sql = "select * from reservation_time where id = ?";
        return jdbcTemplate.queryForObject(sql, (resultSet, rowNumber) -> {
            LocalTime startTime = LocalTime.parse(resultSet.getString("start_at"));
            return new ReservationTime(timeId, startTime);
        }, timeId);
    }

    private long insertReservation(final String name, final LocalDate date, final long timeId) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", name);
        parameters.put("date", date.toString());
        parameters.put("time_id", timeId);
        Number key = simpleJdbcInsert.executeAndReturnKey(parameters);
        return key.longValue();
    }

    @Override
    public void delete(final long id) {
        final String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
