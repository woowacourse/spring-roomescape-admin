package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class Reservations {

    private final AtomicLong index = new AtomicLong(1);
    private final JdbcTemplate jdbcTemplate;

    public Reservations(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> actorRowMapper = (resultSet, rowNum) -> {
        return new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                LocalDate.parse(resultSet.getString("date")),
                LocalTime.parse(resultSet.getString("time"))
        );
    };

    public List<Reservation> findAll() {
        final String sql = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(sql, actorRowMapper);
    }

    public Long add(final String name, final LocalDate date, final LocalTime time) {
        Reservation reservation = new Reservation(index.getAndIncrement(),
                name,
                date,
                time
        );
        reservations.add(reservation);
        return reservation.getId();
    }

    public void remove(final Long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> it.isEqualId(id))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
        reservations.remove(reservation);
    }
}
