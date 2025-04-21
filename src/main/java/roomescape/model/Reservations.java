package roomescape.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class Reservations {

    private final AtomicLong autoIncrementId = new AtomicLong(0L);
    private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();
    private final JdbcTemplate jdbcTemplate;

    public Reservations(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> getAll() {
        String query = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(query, (resultSet, rowNum) -> new Reservation(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getObject("date", LocalDate.class),
            resultSet.getObject("time", LocalTime.class)
        ));
    }

    public Reservation save(Reservation reservation) {
        Reservation saved = reservation.withId(autoIncrementId.incrementAndGet());
        reservations.put(saved.id(), saved);
        return saved;
    }

    public void remove(Long id) {
        reservations.remove(id);
    }
}
