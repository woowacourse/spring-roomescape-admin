package roomescape.controller.api;

import java.sql.Date;
import java.time.LocalTime;
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
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.reservation.ReservationRequest;
import roomescape.dto.reservation.ReservationResponse;
import roomescape.dto.reservationtime.ReservationTimeResponse;

@RequestMapping("/reservations")
@Controller
public class ReservationController {

    private static final RowMapper<Reservation> RESERVATION_ROW_MAPPER = (resultSet, rowNum) -> {
        Long timeId = resultSet.getLong("time_id");
        LocalTime time = resultSet.getTime("start_at").toLocalTime();

        return new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                new ReservationTime(timeId, time));
    };
    private static final RowMapper<ReservationTime> RESERVATION_TIME_ROW_MAPPER = (resultSet, rowNum) ->
            new ReservationTime(
                    resultSet.getLong("id"),
                    resultSet.getTime("start_at").toLocalTime());

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @GetMapping("")
    public ResponseEntity<List<ReservationResponse>> reservations() {
        List<ReservationResponse> reservationResponses = getReservations().stream()
                .map(ReservationResponse::from)
                .toList();

        return ResponseEntity.ok(reservationResponses);
    }

    @PostMapping("")
    public ResponseEntity<ReservationResponse> create(@RequestBody ReservationRequest reservationRequest) {
        Long id = simpleJdbcInsert.executeAndReturnKey(
                        Map.of(
                                "name", reservationRequest.name(),
                                "date", Date.valueOf(reservationRequest.date()),
                                "time_id", reservationRequest.timeId()
                        ))
                .longValue();

        ReservationTime reservationTime = jdbcTemplate.queryForObject(
                "select id, start_at from reservation_time where id = ?",
                RESERVATION_TIME_ROW_MAPPER,
                reservationRequest.timeId());

        ReservationResponse reservationResponse = new ReservationResponse(
                id,
                reservationRequest.name(),
                reservationRequest.date().toString(),
                ReservationTimeResponse.from(reservationTime));

        return ResponseEntity.ok(reservationResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        jdbcTemplate.update("delete from reservation where id = ?", id);

        return ResponseEntity.ok().build();
    }

    private List<Reservation> getReservations() {
        String sql = """
                select
                    r.id as reservation_id,
                    r.name,
                    r.date,
                    t.id as time_id,
                    t.start_at as time_value
                from reservation as r
                inner join reservation_time as t
                on r.time_id = t.id
                """;

        return jdbcTemplate.query(sql, RESERVATION_ROW_MAPPER);
    }
}
