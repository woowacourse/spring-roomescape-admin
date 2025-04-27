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
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            LocalDate date = LocalDate.parse(resultSet.getString("date"));
            long timeId = resultSet.getLong("time_id");
            LocalTime time = LocalTime.parse(resultSet.getString("time_value"));
            ReservationTime reservationTime = new ReservationTime(timeId, time);
            return new Reservation(id, name, date, reservationTime);
        });
        return query;
    }

    // TODO : inner join
    @Override
    public Reservation insert(final Reservation reservation) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", reservation.getName());
        parameters.put("date", reservation.getDate().toString());
        parameters.put("time_id", reservation.getTime().getId());
        long reservationId = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();

        return findByIdWithTime(reservationId);
    }

    private Reservation findByIdWithTime(final long reservationId) {
        String sql = """
                SELECT
                    r.name,
                    r.date,
                    t.id as time_id,
                    t.start_at as time_value
                FROM reservation as r
                inner join reservation_time as t
                on r.time_id = t.id
                WHERE r.id = ?""";
        return jdbcTemplate.queryForObject(sql, (resultSet, rowNumber) -> {
            String name = resultSet.getString("name");
            LocalDate date = LocalDate.parse(resultSet.getString("date"));
            long timeId = resultSet.getLong("time_id");
            LocalTime time = LocalTime.parse(resultSet.getString("time_value"));
            ReservationTime reservationTime = new ReservationTime(timeId, time);
            return new Reservation(reservationId, name, date, reservationTime);
        }, reservationId);
    }

    // TODO : id 없을 경우
    @Override
    public void delete(final long id) {
        final String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
