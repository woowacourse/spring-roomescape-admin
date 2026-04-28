package roomescape.controller;

import java.time.LocalTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.ReservationTimeRequest;
import roomescape.dto.response.ReservationTimeResponse;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertExecutor;

    public TimeController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertExecutor = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<ReservationTime> reservationRowMapper = (rs, rowNum) ->
            ReservationTime.create(
                    rs.getLong("id"),
                    rs.getObject("start_at", LocalTime.class)
            );

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> createReservationTime(@RequestBody ReservationTimeRequest request) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(request);

        Number newId = insertExecutor.executeAndReturnKey(params);

        ReservationTime time = ReservationTime.create(
                newId.longValue(),
                request.startAt()
        );

        return ResponseEntity.ok(ReservationTimeResponse.from(time));
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> getAllTimes() {
        String sql = "SELECT id, start_at FROM reservation_time";

        List<ReservationTime> reservationTimes = jdbcTemplate.query(sql, reservationRowMapper);

        List<ReservationTimeResponse> responses = reservationTimes.stream()
                .map(ReservationTimeResponse::from)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
