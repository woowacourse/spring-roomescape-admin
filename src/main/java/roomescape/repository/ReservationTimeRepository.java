package roomescape.repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.ReservationTime;

@Repository
public class ReservationTimeRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ResponseEntity<ReservationTime> createReservationTime(ReservationTime reservationTime) {
        final String query = "INSERT INTO reservation_time (start_at) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, new String[]{"id"});
            ps.setObject(1, reservationTime.getStartAt());
            return ps;
        }, keyHolder);

        ReservationTime createdReservationTime = ReservationTime.generateWithPrimaryKey(
                reservationTime, Objects.requireNonNull(keyHolder.getKey()).longValue());

        return ResponseEntity.ok(createdReservationTime);
    }

    public ResponseEntity<List<ReservationTime>> readReservationTimes() {
        final String query = "SELECT id, start_at FROM reservation_time";
        List<ReservationTime> reservationTimes = jdbcTemplate.query(
                query,
                (resultSet, rowNum) -> new ReservationTime(
                        resultSet.getLong("id"),
                        resultSet.getTime("start_at").toLocalTime()
                )
        );

        return ResponseEntity.ok(reservationTimes);
    }

    public ResponseEntity<Void> deleteReservationTime(Long id) {
        final String query = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(query, id);
        return ResponseEntity.ok().build();
    }
}
