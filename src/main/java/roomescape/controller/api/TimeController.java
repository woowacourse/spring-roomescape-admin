package roomescape.controller.api;

import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
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

    private static final RowMapper<ReservationTime> RESERVATION_TIME_ROW_MAPPER = (resultSet, rowNum) ->
            new ReservationTime(
                    resultSet.getLong("id"),
                    resultSet.getTime("start_at").toLocalTime()
            );

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public TimeController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    @GetMapping("")
    public ResponseEntity<List<ReservationTimeResponse>> times() {
        List<ReservationTimeResponse> reservationTimeResponses = getReservationTimes().stream()
                .map(ReservationTimeResponse::from)
                .toList();

        return ResponseEntity.ok(reservationTimeResponses);
    }

    @PostMapping("")
    public ResponseEntity<ReservationTimeResponse> create(@RequestBody ReservationTimeRequest reservationTimeRequest) {
        long id = simpleJdbcInsert.executeAndReturnKey(
                        Map.of(
                                "start_at", reservationTimeRequest.startAt()
                        ))
                .longValue();

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
        String sql = "select id, start_at from reservation_time";

        return jdbcTemplate.query(sql, RESERVATION_TIME_ROW_MAPPER);
    }
}
