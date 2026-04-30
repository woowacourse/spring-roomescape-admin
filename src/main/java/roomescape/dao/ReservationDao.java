package roomescape.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;

@Repository
public class ReservationDao {

    private JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> getReservations() {
        String sql = "SELECT r.id, r.name, r.date, t.id, t.start_at FROM reservation AS r INNER JOIN reservation_time AS t ON reservation.time_id = reservation_time.id";

        List<Reservation> reservations = jdbcTemplate.query(sql,
                (resultSet, rowNum) -> {
                    ReservationTime time = new ReservationTime(resultSet.getLong("t.id"), resultSet.getString("t.start_at"));
                    return new Reservation(
                            resultSet.getLong("r.id"),
                            resultSet.getString("r.name"),
                            resultSet.getString("r.date"),
                            time
                    );
                });

        return reservations;
    }

    public long createReservation(ReservationRequest request) {
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, request.name());
            ps.setString(2, request.date());
            ps.setLong(3, request.timeId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public void deleteReservation(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";

        jdbcTemplate.update(sql, id);
    }
}
