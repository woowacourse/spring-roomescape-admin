package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.service.ReservationTimeService;

import javax.sql.DataSource;
import java.net.URI;
import java.util.List;

@RestController
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationTimeController(final ReservationTimeService reservationTimeService, final JdbcTemplate jdbcTemplate, final DataSource dataSource) {
        this.reservationTimeService = reservationTimeService;
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTime>> times() {
        return ResponseEntity.ok(reservationTimeService.findAll());
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> create(@RequestBody ReservationTimeRequest reservationTimeRequest) {
        final ReservationTime savedReservationTime = reservationTimeService.save(reservationTimeRequest);
        return ResponseEntity.created(URI.create("/times/" + savedReservationTime.getId())).body(savedReservationTime);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        final String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, id);

        return ResponseEntity.noContent().build();
    }
}
