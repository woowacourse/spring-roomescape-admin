package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ReservationController {
    private final List<Reservation> reservations = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong index = new AtomicLong(1);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Reservation> reservationRowMapper = (rs, rowNum) -> {
        Reservation reservation = new Reservation(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("date"),
                rs.getString("time")
        );
        return reservation;
    };


    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservationDatum() {
        List<Reservation> reservations = jdbcTemplate.query("SELECT * FROM reservation", reservationRowMapper);
        return ResponseEntity.ok(reservations);
    }


    @PostMapping("/reservations")
    public ResponseEntity<Reservation> addReservationData(@RequestBody final Reservation request) {
        Reservation reservation = new Reservation(index.getAndIncrement(), request);
        reservations.add(reservation);

        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservationsData(@PathVariable final Long id) {
        boolean isRemoved = reservations.removeIf(reservation -> reservation.getId().equals(id));
        if (isRemoved) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
