package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    private static final RowMapper<Reservation> RESERVATION_ROW_MAPPER = (resultSet, rowNumber) -> {
        long id = resultSet.getLong("reservation_id");
        String name = resultSet.getString("name");
        LocalDate date = LocalDate.parse(resultSet.getString("date"));
        long timeId = resultSet.getLong("time_id");
        LocalTime time = LocalTime.parse(resultSet.getString("time_value"));
        ReservationTime reservationTime = new ReservationTime(timeId, time);
        return new Reservation(id, name, date, reservationTime);
    };

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
        final List<Reservation> query = jdbcTemplate.query(sql, RESERVATION_ROW_MAPPER);
        return query;
    }

    @Override
    public Reservation save(final Reservation reservation) {
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
                    r.id as reservation_id,
                    r.name,
                    r.date,
                    t.id as time_id,
                    t.start_at as time_value
                FROM reservation as r
                inner join reservation_time as t
                on r.time_id = t.id
                WHERE r.id = ?""";
        return jdbcTemplate.queryForObject(sql, RESERVATION_ROW_MAPPER, reservationId);
    }

    // TODO : id 없을 경우
    @Override
    public void delete(final long id) {
        final String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
