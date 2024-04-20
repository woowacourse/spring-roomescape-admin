package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationTimeResponse;
import roomescape.dto.ReservationTimeSaveRequest;
import roomescape.model.ReservationTime;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert reservationTimeInsert;
    private final RowMapper<ReservationTime> reservationTimeConvertor = (selectedTime, RowNum) ->
            new ReservationTime(selectedTime.getLong("id"), selectedTime.getString("start_at"));

    public ReservationTimeController(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.reservationTimeInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_times")
                .usingGeneratedKeyColumns("id");
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> getTimes() {
        final String selectQuery = "SELECT id, start_at FROM reservation_times";
        final List<ReservationTimeResponse> reservationTimeResponses = jdbcTemplate.query(selectQuery, reservationTimeConvertor)
                .stream()
                .map(ReservationTimeResponse::from)
                .toList();
        return ResponseEntity.ok(reservationTimeResponses);
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> saveTime(@RequestBody final ReservationTimeSaveRequest reservationTimeSaveRequest) {
        final Map<String, String> timeParameters = Map.of("start_at", reservationTimeSaveRequest.startAt());
        final Long savedTimeId = reservationTimeInsert.executeAndReturnKey(timeParameters).longValue();
        final ReservationTimeResponse timeResponse = new ReservationTimeResponse(savedTimeId, reservationTimeSaveRequest.startAt());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(timeResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(final @PathVariable("id") Long id) {
        int affectedRowCount = jdbcTemplate.update("DELETE FROM reservation_times WHERE id = ?", id);
        if (affectedRowCount == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.noContent().build();
    }
}
