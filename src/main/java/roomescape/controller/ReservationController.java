package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationCreateRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ReservationController {
    private final JdbcTemplate jdbcTemplate;
    private final List<Reservation> reservations;
    private final AtomicLong index;
    private final RowMapper<Reservation> rowMapper;

    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.reservations = new ArrayList<>();
        this.index = new AtomicLong(1);
        this.rowMapper = (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                resultSet.getString("time")
        );
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> readReservations() {
        String sql = "select id, name, date, time from reservation";
        return ResponseEntity.ok(jdbcTemplate.query(sql, rowMapper));
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationCreateRequest dto) {
        Reservation reservation = dto.createReservation(index.getAndIncrement());
        reservations.add(reservation);
        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable(name = "id") long id) {
        Reservation findReservation = reservations.stream()
                .filter(reservation -> reservation.checkSameId(id))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 id(%d)의 예약이 존재하지 않습니다.".formatted(id)));

        reservations.remove(findReservation);
        return ResponseEntity.ok().build();
    }
}
