package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.service.ReservationService;

import javax.sql.DataSource;
import java.net.URI;
import java.util.List;

@RestController
public class ReservationController {
    private final ReservationService reservationService;
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationController(final ReservationService reservationService, final JdbcTemplate jdbcTemplate, final DataSource dataSource) {
        this.reservationService = reservationService;
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reservations() {
        return ResponseEntity.ok(reservationService.findAll());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody ReservationRequest reservationRequest) {
        final Reservation reservation = reservationService.create(reservationRequest);
        return ResponseEntity.created(URI.create("/reservations/" + reservation.getId())).body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        final String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);

        return ResponseEntity.noContent().build();
    }
}
