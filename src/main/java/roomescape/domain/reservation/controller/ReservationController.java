package roomescape.domain.reservation.controller;

import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.reservation.entity.Reservation;
import roomescape.domain.reservation.request.ReservationCreateRequest;
import roomescape.domain.reservation.response.ReservationResponse;

@RestController
public class ReservationController {

    private static final String FIND_ALL_RESERVATIONS_QUERY = """
            SELECT * FROM reservation;
            """;

    private static final String DELETE_RESERVATION_BY_ID_QUERY = """
            DELETE FROM reservation
            WHERE id = ?
            """;

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationController(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> findAll() {
        List<Reservation> reservations = jdbcTemplate.query(FIND_ALL_RESERVATIONS_QUERY, reservationRowMapper());

        List<ReservationResponse> results = reservations.stream()
                .map(ReservationResponse::from)
                .toList();

        return ResponseEntity.ok(results);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> save(@RequestBody ReservationCreateRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("reservation이 null 입니다.");
        }

        String reservationName = request.name();
        LocalDate reservationDate = request.date();
        LocalTime reservationTime = request.time();

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", reservationName)
                .addValue("date", reservationDate)
                .addValue("time", reservationTime);

        Number key = simpleJdbcInsert.executeAndReturnKey(parameters);

        Long generatedId = key.longValue();

        ReservationResponse response = new ReservationResponse(
                generatedId,
                reservationName,
                reservationDate,
                reservationTime
        );

        return ResponseEntity.ok()
                .header(HttpHeaders.LOCATION, String.valueOf(response.id()))
                .body(response);
    }

    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long reservationId) {
        jdbcTemplate.update(DELETE_RESERVATION_BY_ID_QUERY, reservationId);
        return ResponseEntity.ok().build();
    }

    private RowMapper<Reservation> reservationRowMapper() {
        return (resultSet, rowNumber) -> new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                LocalDate.parse(resultSet.getString("date")),
                LocalTime.parse(resultSet.getString("time"))
        );
    }
}
