package roomescape.controller;

import java.net.URI;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationTimeController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(
                Objects.requireNonNull(jdbcTemplate.getDataSource()))
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> post(@RequestBody ReservationTimeRequest request) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(request);
        Number key = simpleJdbcInsert.executeAndReturnKey(parameterSource);
        return ResponseEntity.created(URI.create("/times/" + key))
                .body(new ReservationTimeResponse(
                        key.longValue(),
                        request.startAt()
                ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBy(@PathVariable Long id) {
        String sql = "delete from reservation_time where id = ?";
        jdbcTemplate.update(sql, id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> getReservationTime() {
        String sql = "select * from reservation_time";
        List<ReservationTimeResponse> times = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new ReservationTimeResponse(
                        rs.getLong("id"),
                        LocalTime.parse(rs.getString("start_at"))
                ));

        return ResponseEntity.ok(times);
    }
}
