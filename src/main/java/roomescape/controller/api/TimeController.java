package roomescape.controller.api;

import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.domain.ReservationTime;
import roomescape.dto.reservationtime.ReservationTimeRequest;
import roomescape.dto.reservationtime.ReservationTimeResponse;

@RequestMapping("/times")
@Controller
public class TimeController {

    private JdbcTemplate jdbcTemplate;

    public TimeController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("")
    public ResponseEntity<List<ReservationTimeResponse>> times() {
        List<ReservationTimeResponse> reservationTimeResponses = getReservationTimes().stream()
                .map(ReservationTimeResponse::from)
                .toList();

        return ResponseEntity.ok()
                .body(reservationTimeResponses);
    }

    @PostMapping("")
    public ResponseEntity<ReservationTimeResponse> create(@RequestBody ReservationTimeRequest reservationTimeRequest) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into reservation_time (start_at) values (?)",
                    new String[]{"id"});
            ps.setTime(1, Time.valueOf(reservationTimeRequest.startAt()));
            return ps;
        }, keyHolder);
        Long id = keyHolder.getKey().longValue();
        ReservationTimeResponse reservationTimeResponse = new ReservationTimeResponse(id,
                reservationTimeRequest.startAt().toString());

        return ResponseEntity.ok(reservationTimeResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        jdbcTemplate.update("delete from reservation_time where id = ?", id);

        return ResponseEntity.ok().build();
    }

    private List<ReservationTime> getReservationTimes() {
        return jdbcTemplate.query(
                "select id, start_at from reservation_time",
                (resultSet, rowNum) ->
                        new ReservationTime(
                                resultSet.getLong("id"),
                                resultSet.getTime("start_at").toLocalTime()
                        )
        );
    }
}
