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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.dto.ReservationTimeResponse;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("reservation")
            .usingGeneratedKeyColumns("id");
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getAll() {
        List<ReservationResponse> reservationResponses = jdbcTemplate.query(
            "SELECT "
                + "r.id as reservation_id, "
                + "r.name, "
                + "r.date, "
                + "t.id as time_id, "
                + "t.start_at as time_value "
                + "FROM reservation as r "
                + "inner join reservation_time as t "
                + "on r.time_id = t.id",
            (resultSet, rowNum) ->
                new ReservationResponse(
                    resultSet.getLong("reservation_id"),
                    resultSet.getString("name"),
                    resultSet.getString("date"),
                    new ReservationTimeResponse(
                        resultSet.getLong("time_id"),
                        LocalTime.parse(
                            resultSet.getString("time_value"),
                            DateTimeFormatter.ofPattern("HH:mm")
                        )
                    )
                )
        );
        return ResponseEntity.ok().body(reservationResponses);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<ReservationResponse> create(@RequestBody ReservationRequest request) {
        ReservationTime reservationTime = jdbcTemplate.queryForObject(
            "select * from reservation_time where id = ?",
            (resultSet, rowNum) ->
                new ReservationTime(
                    request.timeId(),
                    LocalTime.parse(
                        resultSet.getString("start_at"),
                        DateTimeFormatter.ofPattern("HH:mm")
                    )
                ),
            request.timeId()
        );
        Reservation reservation = new Reservation(request.name(), request.date(), reservationTime);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", reservation.getName());
        parameters.put("date", reservation.getDate());
        parameters.put("time_id", reservationTime.getId());
        Long id = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
        return ResponseEntity.ok().body(
            new ReservationResponse(
                id,
                reservation.getName(),
                reservation.getDate(),
                new ReservationTimeResponse(
                    reservationTime.getId(),
                    reservationTime.getStartAt()
                )
            ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
        int update = jdbcTemplate.update(
            "delete from reservation where id = ?",
            id
        );
        if (update == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
