package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationTimeResponse;
import roomescape.model.ReservationTime;

import java.util.List;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<ReservationTime> reservationTimeConvertor = (selectedTime, RowNum) ->
            new ReservationTime(selectedTime.getLong("id"), selectedTime.getString("start_at"));

    public ReservationTimeController(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
}
