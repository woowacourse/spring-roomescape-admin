package roomescape.controller.api;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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

    private JdbcTemplate jdbcTemplate;

    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("")
    public ResponseEntity<List<ReservationResponse>> reservations() {
        List<ReservationResponse> reservationResponses = getReservations().stream()
                .map(ReservationResponse::from)
                .toList();

        return ResponseEntity.ok()
                .body(reservationResponses);
    }

    @PostMapping("")
    public ResponseEntity<ReservationResponse> create(@RequestBody ReservationRequest reservationRequest) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into reservation (name, date, time_id) values (?, ?, ?)",
                    new String[]{"id"});
            ps.setString(1, reservationRequest.name());
            ps.setDate(2, Date.valueOf(reservationRequest.date()));
            ps.setLong(3, reservationRequest.timeId());
            return ps;
        }, keyHolder);
        Long id = keyHolder.getKey().longValue();

        ReservationTimeResponse reservationTimeResponse = jdbcTemplate.queryForObject(
                "select start_at from reservation_time where id = ?",
                (resultSet, rowNum) -> {
                    ReservationTimeResponse foundReservationTimeResponse = new ReservationTimeResponse(
                            reservationRequest.timeId(),
                            resultSet.getString("start_at")
                    );
                    return foundReservationTimeResponse;
                }, reservationRequest.timeId());

        ReservationResponse reservationResponse = new ReservationResponse(id, reservationRequest.name(),
                reservationRequest.date().toString(), reservationTimeResponse);

        return ResponseEntity.ok(reservationResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        jdbcTemplate.update("delete from reservation where id = ?", id);

        return ResponseEntity.ok().build();
    }

    private List<Reservation> getReservations() {
        return jdbcTemplate.query(
                """
                        SELECT
                            r.id as reservation_id,
                            r.name,
                            r.date,
                            t.id as time_id,
                            t.start_at as time_value
                        FROM reservation as r
                        inner join reservation_time as t
                        on r.time_id = t.id
                        """,
                (resultSet, rowNum) -> {
                    Long timeId = resultSet.getLong("time_id");
                    LocalTime time = resultSet.getTime("start_at").toLocalTime();

                    return new Reservation(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("date"),
                            new ReservationTime(timeId, time)
                    );
                }
        );
    }
}
