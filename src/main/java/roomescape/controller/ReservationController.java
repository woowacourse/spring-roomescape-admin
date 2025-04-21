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
import roomescape.Reservation;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping()
    ResponseEntity<List<Reservation>> read() {
        final String query = "SELECT id, name, date, time FROM reservation";
        List<Reservation> reservList = jdbcTemplate.query(
                query,
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        LocalDate.now(),
                        LocalTime.now()
                )
        );

        return ResponseEntity.ok(reservList);
    }

    @PostMapping
    ResponseEntity<Reservation> create(@RequestBody Reservation reservation) {
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

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id) {
        final String query = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(query, id);
        return ResponseEntity.ok().build();
    }
}
