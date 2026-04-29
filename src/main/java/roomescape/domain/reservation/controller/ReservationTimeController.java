package roomescape.domain.reservation.controller;

import java.time.LocalTime;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.reservation.entity.ReservationTime;
import roomescape.domain.reservation.request.ReservationTimeCreateRequest;
import roomescape.domain.reservation.response.ReservationTimeResponse;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private static final String FIND_ALL_RESERVATION_TIMES_QUERY = """
            SELECT * FROM reservation_time;
            """;

    private static final String DELETE_RESERVATION_TIME_BY_ID_QUERY = """
            DELETE FROM reservation_time
            WHERE id = ?;
            """;

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationTimeController(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> findAll() {
        List<ReservationTime> reservations = jdbcTemplate.query(FIND_ALL_RESERVATION_TIMES_QUERY,
                reservationTimeRowMapper());

        List<ReservationTimeResponse> results = reservations.stream()
                .map(ReservationTimeResponse::from)
                .toList();

        return ResponseEntity.ok(results);
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> save(@RequestBody ReservationTimeCreateRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("reservationTime이 null 입니다.");
        }

        LocalTime startAt = request.startAt();

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("startAt", startAt);

        Number key = simpleJdbcInsert.executeAndReturnKey(parameters);
        Long generatedId = key.longValue();

        ReservationTimeResponse response = new ReservationTimeResponse(generatedId, startAt);

        return ResponseEntity.ok()
                .header(HttpHeaders.LOCATION, String.valueOf(generatedId))
                .body(response);
    }

    @DeleteMapping("/{reservationTimeId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long reservationTimeId) {
        jdbcTemplate.update(DELETE_RESERVATION_TIME_BY_ID_QUERY, reservationTimeId);
        return ResponseEntity.ok().build();
    }

    private RowMapper<ReservationTime> reservationTimeRowMapper() {
        return (resultSet, rowNumber) -> new ReservationTime(
                resultSet.getLong("id"),
                LocalTime.parse(resultSet.getString("start_a"))
        );
    }
}
