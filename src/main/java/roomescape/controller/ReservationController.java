package roomescape.controller;

import java.net.URI;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationSaveRequest;
import roomescape.dto.ReservationResponse;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert reservationInsert;
    private final RowMapper<Reservation> reservationConvertor = (selectedReservation, RowNum) -> {
        ReservationTime time = new ReservationTime(selectedReservation.getLong("time_id"), selectedReservation.getString("start_at"));
        return new Reservation(
                selectedReservation.getLong("id"),
                selectedReservation.getString("name"),
                selectedReservation.getString("date"), time);
    };
    private final RowMapper<ReservationTime> reservationTimeConvertor = (selectedTime, RowNum) ->
            new ReservationTime(selectedTime.getLong("id"), selectedTime.getString("start_at"));

    public ReservationController(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.reservationInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservations")
                .usingGeneratedKeyColumns("id");
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        final String selectQuery = """
            SELECT
                r.id as reservation_id,
                r.name,
                r.date,
                t.id as time_id,
                t.start_at
            FROM reservations as r
            INNER JOIN reservation_times as t
            ON r.time_id = t.id
        """;
        final List<ReservationResponse> reservationResponses = jdbcTemplate.query(selectQuery, reservationConvertor)
                .stream()
                .map(ReservationResponse::from)
                .toList();
        return ResponseEntity.ok(reservationResponses);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> saveReservation(
            @RequestBody final ReservationSaveRequest reservationSaveRequest) {
        final String selectQuery = "SELECT id, start_at FROM reservation_times WHERE id = ?";
        final ReservationTime time;
        try {
            time = jdbcTemplate.queryForObject(selectQuery, reservationTimeConvertor, reservationSaveRequest.timeId());
        } catch (EmptyResultDataAccessException exception) {
            return ResponseEntity.notFound().build();
        }
        final SqlParameterSource reservationParameters = new MapSqlParameterSource()
                .addValue("name", reservationSaveRequest.name())
                .addValue("date", reservationSaveRequest.date())
                .addValue("time_id", reservationSaveRequest.timeId());
        final Long savedReservationId = reservationInsert.executeAndReturnKey(reservationParameters).longValue();
        final Reservation savedReservation = new Reservation(savedReservationId, reservationSaveRequest.name(), reservationSaveRequest.date(), time);
        return ResponseEntity.created(URI.create("/reservations/" + savedReservationId))
                .body(ReservationResponse.from(savedReservation));
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
