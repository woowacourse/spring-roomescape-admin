package roomescape;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ResponseEntity<List<Reservation>> readReservations() {
        final String query = "SELECT id, name, date, time FROM reservation";
        List<Reservation> reservations = jdbcTemplate.query(
                query,
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getTime("time").toLocalTime()
                )
        );

        return ResponseEntity.ok(reservations);
    }

    public ResponseEntity<Reservation> createReservation(Reservation reservation) {
        final String query = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setObject(2, reservation.getDate());
            ps.setObject(3, reservation.getTime());
            return ps;
        }, keyHolder);

        Reservation newReservation = Reservation.toEntity(reservation, keyHolder.getKey().longValue());
        return ResponseEntity.ok().body(newReservation);
    }

    public ResponseEntity<Void> deleteReservation(Long id) {
        final String query = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(query, id);
        return ResponseEntity.ok().build();
    }
}
