package roomescape.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationSaveRequest;
import roomescape.dto.ReservationResponse;
import roomescape.model.Reservation;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert reservationInsert;
    private final RowMapper<Reservation> reservationConvertor = (selectedReservation, RowNum) -> new Reservation(
            selectedReservation.getLong("id"),
            selectedReservation.getString("name"),
            selectedReservation.getString("date"),
            selectedReservation.getString("time"));

    public ReservationController(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.reservationInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservations")
                .usingGeneratedKeyColumns("id");
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        final String selectQuery = "SELECT id, name, date, time FROM reservations";
        final List<ReservationResponse> reservationResponses = jdbcTemplate.query(selectQuery, reservationConvertor)
                .stream()
                .map(ReservationResponse::from)
                .toList();
        return ResponseEntity.ok(reservationResponses);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> saveReservation(
            @RequestBody final ReservationSaveRequest reservationSaveRequest) {
        final Map<String, String> reservationParameters = Map.of(
                "name", reservationSaveRequest.name(),
                "date", reservationSaveRequest.date(),
                "time", reservationSaveRequest.time());
        final Long savedReservationId = reservationInsert.executeAndReturnKey(reservationParameters).longValue();
        final ReservationResponse reservationResponse = new ReservationResponse(savedReservationId,
                reservationSaveRequest.name(), reservationSaveRequest.date(), reservationSaveRequest.time());
        return ResponseEntity.created(URI.create("/reservations/" + savedReservationId))
                .body(reservationResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(final @PathVariable("id") Long id) {
        int affectedRowCount = jdbcTemplate.update("DELETE FROM reservations WHERE id = ?", id);
        if (affectedRowCount == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.noContent().build();
    }
}
