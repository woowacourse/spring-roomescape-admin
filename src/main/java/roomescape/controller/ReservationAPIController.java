package roomescape.controller;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;

@RestController
@RequestMapping("/reservations")
public class ReservationAPIController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public ResponseEntity<List<Reservation>> searchReservations() {
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

        List<Reservation> reservations = jdbcTemplate.query(sql, (rs, rowNum) -> {
            ReservationTime reservationTime = new ReservationTime(
                    rs.getLong("time_id"),
                    LocalTime.parse(rs.getString("time_value"))
            );
            return new Reservation(
                    rs.getLong("reservation_id"),
                    rs.getString("name"),
                    LocalDate.parse(rs.getString("date")),
                    reservationTime
            );
        });
        return ResponseEntity.ok(reservations);
    }

    @PostMapping
    public ResponseEntity<Reservation> addReservation(@RequestBody ReservationRequest reservationRequest) {
        String sql = "INSERT INTO reservation(name, date, time_id) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservationRequest.getName());
            ps.setString(2, reservationRequest.getDate().toString());
            ps.setString(3, reservationRequest.getTimeId().toString());
            return ps;
        }, keyHolder);

        ReservationTime reservationTime = findReservationTimeById(reservationRequest.getTimeId());
        Reservation reservation = new Reservation(
                keyHolder.getKey().longValue(),
                reservationRequest.getName(),
                reservationRequest.getDate(),
                reservationTime
        );
        return ResponseEntity.ok(reservation);
    }

    private ReservationTime findReservationTimeById(Long id) {
        String sql = "SELECT start_at FROM reservation_time WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                new ReservationTime(
                        id,
                        LocalTime.parse(rs.getString("start_at"))
                ), id
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
        return ResponseEntity.ok().build();
    }
}
