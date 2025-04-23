package roomescape.controller.reservationtime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.reservationtime.request.ReservationTimeRequest;
import roomescape.controller.reservationtime.response.ReservationTimeResponse;
import roomescape.model.ReservationTime;

@RequestMapping("/times")
@RestController
public class ReservationTimeController {

    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insertActor;

    public ReservationTimeController(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertActor = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    @PostMapping
    ResponseEntity<ReservationTimeResponse> save(@RequestBody ReservationTimeRequest reservationTimeRequest) {
        ReservationTime time = reservationTimeRequest.toTime();
        Long id = saveAndGetId(time);
        return ResponseEntity.ok(ReservationTimeResponse.from(id, time));
    }

    @GetMapping
    ResponseEntity<List<ReservationTimeResponse>> read() {
        final String sql = "select id, start_at from reservation_time";
        final RowMapper<ReservationTime> rowMapper = getRowMapper();
        final List<ReservationTime> times = jdbcTemplate.query(sql, rowMapper);

        final List<ReservationTimeResponse> reservationTimeResponses = times.stream()
                .map(ReservationTimeResponse::of)
                .toList();

        return ResponseEntity.ok(reservationTimeResponses);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id) {
        final String sql = "delete from reservation_time where id = ?";
        jdbcTemplate.update(sql, id);
        return ResponseEntity.ok().build();
    }

    private Long saveAndGetId(final ReservationTime time) {
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("start_at", time.getStartAt());
        return getGenerateId(parameters);
    }

    private Long getGenerateId(final Map<String, Object> parameters) {
        Number number = insertActor.executeAndReturnKey(parameters);
        return number.longValue();
    }

    private RowMapper<ReservationTime> getRowMapper() {
        return (resultSet, rowNum) ->
                ReservationTime.from(
                        resultSet.getLong("id"),
                        resultSet.getTime("start_at").toLocalTime());
    }
}
