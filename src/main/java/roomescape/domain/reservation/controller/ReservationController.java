package roomescape.domain.reservation.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
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
import roomescape.domain.reservation.entity.ReservationTime;
import roomescape.domain.reservation.request.ReservationCreateRequest;
import roomescape.domain.reservation.response.ReservationResponse;
import roomescape.domain.reservation.response.ReservationTimeResponse;

@RestController
public class ReservationController {

    private static final String FIND_ALL_RESERVATIONS_QUERY = """
            SELECT 
                r.id AS reservation_id,
                r.name,
                r.date,
                t.id AS time_id,
                t.start_at AS time_value
            FROM reservation AS r
            INNER JOIN reservation_time AS t
                ON r.time_id = t.id;
            """;

    private static final String FIND_RESERVATION_TIME_BY_ID_QUERY = """
            SELECT * FROM reservation_time
            WHERE id = ?;
            """;

    private static final String DELETE_RESERVATION_BY_ID_QUERY = """
            DELETE FROM reservation
            WHERE id = ?;
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
        List<ReservationResponse> reservations = jdbcTemplate.query(
                FIND_ALL_RESERVATIONS_QUERY,
                reservationResponseRowMapper()
        );

        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> save(@RequestBody ReservationCreateRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("reservation이 null 입니다.");
        }

        Long reservationTimeId = request.timeId();
        ReservationTime reservationTime = getReservationTimeById(reservationTimeId);

        String reservationName = request.name();
        LocalDate reservationDate = request.date();

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", reservationName)
                .addValue("date", reservationDate)
                .addValue("time_id", reservationTimeId);

        Number key = simpleJdbcInsert.executeAndReturnKey(parameters);
        Long generatedId = key.longValue();

        ReservationResponse response = new ReservationResponse(
                generatedId,
                reservationName,
                reservationDate,
                new ReservationTimeResponse(
                        reservationTime.getId(),
                        reservationTime.getStartAt()
                )
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

    private ReservationTime getReservationTimeById(Long reservationTimeId) {
        try {
            return jdbcTemplate.queryForObject(
                    FIND_RESERVATION_TIME_BY_ID_QUERY,
                    reservationTimeRowMapper(),
                    reservationTimeId
            );
        } catch (EmptyResultDataAccessException exception) {
            throw new IllegalArgumentException("존재하지 않는 시간입니다. time_id=" + reservationTimeId);
        }
    }

    private RowMapper<ReservationResponse> reservationResponseRowMapper() {
        return (resultSet, rowNumber) -> new ReservationResponse(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                LocalDate.parse(resultSet.getString("date")),
                new ReservationTimeResponse(
                        resultSet.getLong("id"),
                        LocalTime.parse(resultSet.getString("start_at"))
                )
        );
    }

    private RowMapper<ReservationTime> reservationTimeRowMapper() {
        return (resultSet, rowNumber) -> new ReservationTime(
                resultSet.getLong("id"),
                LocalTime.parse(resultSet.getString("start_at"))
        );
    }
}
