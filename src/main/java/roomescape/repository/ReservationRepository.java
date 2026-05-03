package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAllReservations() {
        String sql = """
                SELECT 
                    r.id AS reservation_id,
                    r.name,
                    r.date,
                    t.id AS time_id,
                    t.start_at AS time_value
                FROM reservation AS r
                INNER JOIN reservation_time AS t
                  ON r.time_id = t.id
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ReservationTime time = new ReservationTime(
                    rs.getLong("time_id"),
                    rs.getTime("time_value").toLocalTime()
            );
            return new Reservation(
                    rs.getLong("reservation_id"),
                    rs.getString("name"),
                    rs.getDate("date").toLocalDate(),
                    time
            );
        });
    }

    public Reservation add(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setDate(2, Date.valueOf(reservation.getDate()));
            ps.setLong(3, reservation.getReservationTime().getId());
            return ps;
        }, keyHolder);

        Long generatedId = keyHolder.getKey().longValue();
        return new Reservation(generatedId, reservation.getName(), reservation.getDate(), reservation.getReservationTime());
    }

    public void remove(Long id) {
        jdbcTemplate.update("DELETE from reservation where id = ?", id);
    }
}
