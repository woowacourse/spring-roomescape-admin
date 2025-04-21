package roomescape.domain;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class Reservations {

    private final JdbcTemplate jdbcTemplate;

    public Reservations(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        final String sql = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(sql, actorRowMapper);
    }

    public Long add(final String name, final LocalDate date, final LocalTime time) {
        final String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    new String[]{"id"});
            ps.setString(1, name);
            ps.setObject(2, date);
            ps.setObject(3, time);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public void remove(final Long id) {
        final String sql = "DELETE reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private final RowMapper<Reservation> actorRowMapper = (resultSet, rowNum) -> {
        return new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                LocalDate.parse(resultSet.getString("date")),
                LocalTime.parse(resultSet.getString("time"))
        );
    };
}
