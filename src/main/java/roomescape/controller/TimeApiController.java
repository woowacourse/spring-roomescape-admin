package roomescape.controller;

import java.sql.PreparedStatement;
import java.util.List;
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
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;

@RestController
@RequestMapping("/times")
public class TimeApiController {

    private final JdbcTemplate jdbcTemplate;

    public TimeApiController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> register(@RequestBody ReservationTimeRequest request) {
        ReservationTime reservationTime = new ReservationTime(request.startAt());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into reservation_time(start_at) values (?)", new String[]{"id"});
            ps.setString(1, reservationTime.getStartAt().toString());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        ReservationTime saved = new ReservationTime(id, reservationTime.getStartAt());
        return ResponseEntity.ok(ReservationTimeResponse.from(saved));
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> getAllTimes() {
        List<ReservationTime> reservations = jdbcTemplate.query(
                "select * from reservation_time",
                (resultSet, rowNum) -> new ReservationTime(
                        resultSet.getLong("id"),
                        resultSet.getTime("start_at").toLocalTime()
                ));
        List<ReservationTimeResponse> response = reservations.stream()
                .map(ReservationTimeResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        jdbcTemplate.update("delete from reservation_time where id = ?", id);
        return ResponseEntity.ok().build();
    }
}
