package roomescape.dao;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import roomescape.exception.ReservationNotFoundException;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

@Component
public class ReservationDao {
    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Reservation save(String name, String date, ReservationTime time) {
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, name);
            ps.setString(2, date);
            ps.setLong(3, time.id());
            return ps;
        }, keyHolder);

        long generatedId = keyHolder.getKey().longValue();

        return new Reservation(generatedId, name, date, time);
    }

    public List<Reservation> findAll() {
        String sql = "SELECT r.id AS reservation_id, r.name, r.date, t.id AS time_id, t.start_at AS time_value " +
                "FROM reservation AS r " +
                "INNER JOIN reservation_time AS t ON r.time_id = t.id";

        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new Reservation(
                        rs.getLong("reservation_id"),
                        rs.getString("name"),
                        rs.getString("date"),
                        new ReservationTime(rs.getLong("time_id"), rs.getString("time_value"))
                ));
    }

    public void delete(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        int affectedRows = jdbcTemplate.update(sql, id);

        if (affectedRows == 0) {
            throw new ReservationNotFoundException(id);
        }
    }

}
