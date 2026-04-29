package roomescape.controller;

import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.request.ReservationTimeRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.dto.response.ReservationTimeResponse;

@RestController
public class ReservationController {

    private final JdbcTemplate jdbcTemplate;

    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {
        String sql = "SELECT"
                + "    r.id as reservation_id,"
                + "    r.name,"
                + "    r.date,"
                + "    t.id as time_id,"
                + "    t.start_at as time_value"
                + " FROM reservation as r"
                + " INNER JOIN reservation_time as t"
                + "  ON r.time_id = t.id";

        List<Reservation> reservations = jdbcTemplate.query(sql, reservationRowMapper);

        List<ReservationResponse> responses = reservations.stream()
                .map(ReservationResponse::from)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest request) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(request);
        SimpleJdbcInsert reservationInsertExecutor = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");

        Number reservationId = reservationInsertExecutor.executeAndReturnKey(params);

        String selectSql = "SELECT"
                + "    r.id as reservation_id,"
                + "    r.name,"
                + "    r.date,"
                + "    t.id as time_id,"
                + "    t.start_at as time_value"
                + " FROM reservation as r"
                + " INNER JOIN reservation_time as t ON r.time_id = t.id"
                + " WHERE r.id = ?";

        Reservation reservation = jdbcTemplate.queryForObject(
                selectSql,
                reservationRowMapper,
                reservationId.longValue());

        return ResponseEntity.ok(ReservationResponse.from(reservation));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTimeResponse> createReservationTime(@RequestBody ReservationTimeRequest request) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(request);
        SimpleJdbcInsert insertExecutor = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");

        Number newId = insertExecutor.executeAndReturnKey(params);

        ReservationTime time = ReservationTime.create(
                newId.longValue(),
                request.startAt()
        );

        return ResponseEntity.ok(ReservationTimeResponse.from(time));
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTimeResponse>> getAllTimes() {
        String sql = "SELECT id, start_at FROM reservation_time";

        List<ReservationTime> reservationTimes = jdbcTemplate.query(sql, reservationTimeRowMapper);

        List<ReservationTimeResponse> responses = reservationTimes.stream()
                .map(ReservationTimeResponse::from)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    private final RowMapper<Reservation> reservationRowMapper = (rs, rowNum) -> {
        ReservationTime reservationTime = ReservationTime.create(
                rs.getLong("time_id"),
                rs.getObject("time_value", LocalTime.class)
        );

        return Reservation.create(
                rs.getLong("reservation_id"),
                rs.getString("name"),
                rs.getObject("date", LocalDate.class),
                reservationTime
        );
    };

    private final RowMapper<ReservationTime> reservationTimeRowMapper = (rs, rowNum) ->
            ReservationTime.create(
                    rs.getLong("id"),
                    rs.getObject("start_at", LocalTime.class)
            );
}
