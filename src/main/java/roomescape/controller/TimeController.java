package roomescape.controller;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;

@Controller
@RequestMapping("/times")
public class TimeController {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public TimeController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("reservation_time")
            .usingGeneratedKeyColumns("id");
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> create(@RequestBody ReservationTimeRequest request) {
        ReservationTime reservationTime = new ReservationTime(
            LocalTime.parse(request.startAt(), DateTimeFormatter.ofPattern("HH:mm"))
        );
        Map<String, String> parameters = new HashMap<>();
        parameters.put("start_at", reservationTime.getStartAt().toString());
        Long id = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
        return ResponseEntity.ok().body(new ReservationTimeResponse(
            id,
            reservationTime.getStartAt()
        ));
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> getAll() {
        List<ReservationTimeResponse> responses = jdbcTemplate.query(
            "select * from reservation_time",
            (resultSet, rowNum) -> {
                LocalTime startAt = LocalTime.parse(
                    resultSet.getString("start_at"),
                    DateTimeFormatter.ofPattern("HH:mm")
                );
                return new ReservationTimeResponse(
                    resultSet.getLong("id"),
                    startAt
                );
            });
        return ResponseEntity.ok().body(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        int update = jdbcTemplate.update(
            "delete from reservation_time where id = ?",
            id
        );
        if(update == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
