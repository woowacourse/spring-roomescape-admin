package roomescape.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.dao.DataAccessException;
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
        String sql = "SELECT r.id AS r_id, r.name, r.date, t.id AS t_id, t.start_at FROM reservation AS r INNER JOIN reservation_time AS t ON r.time_id = t.id";

        List<Reservation> reservations = jdbcTemplate.query(sql,
                (resultSet, rowNum) -> {
                    ReservationTime time = new ReservationTime(resultSet.getLong("t_id"), resultSet.getString("start_at"));
                    return new Reservation(
                            resultSet.getLong("r_id"),
                            resultSet.getString("name"),
                            resultSet.getString("date"),
                            time
                    );
                });

        return reservations;
    }

    public long createReservation(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate());
            ps.setLong(3, reservation.getTime().getId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public void deleteReservation(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";

        try {
            int rowAffected = jdbcTemplate.update(sql, id);
            if (rowAffected == 0) {
                throw new NoSuchElementException();
            }
        } catch (DataAccessException e) {
            throw new IllegalStateException();
        }

    }
}
