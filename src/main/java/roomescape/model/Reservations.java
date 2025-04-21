package roomescape.model;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class Reservations {

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
        String query = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, new String[] {"id"});
            ps.setString(1, reservation.name());
            ps.setObject(2, reservation.date());
            ps.setObject(3, reservation.time());
            return ps;
        }, keyHolder);
        return reservation.withId(keyHolder.getKey().longValue());
    }

    public void remove(Long id) {
        String query = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(query, id);
    }
}
