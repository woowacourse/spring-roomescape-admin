package roomescape.controller;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalDateTime;
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
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

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
                    "insert into reservation (name, date, time) values (?, ?, ?)",
                    new String[]{"id"});
            ps.setString(1, reservationRequest.name());
            ps.setDate(2, Date.valueOf(reservationRequest.date()));
            ps.setTime(3, Time.valueOf(reservationRequest.time()));
            return ps;
        }, keyHolder);
        Long id = keyHolder.getKey().longValue();
        ReservationResponse reservationResponse = new ReservationResponse(id, reservationRequest.name(),
                reservationRequest.date().toString(), reservationRequest.time().toString());

        return ResponseEntity.ok(reservationResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        jdbcTemplate.update("delete from reservation where id = ?", id);

        return ResponseEntity.ok().build();
    }

    private List<Reservation> getReservations() {
        return jdbcTemplate.query(
                "select id, name, date, time from reservation",
                (resultSet, rowNum) ->
                        new Reservation(
                                resultSet.getLong("id"),
                                resultSet.getString("name"),
                                LocalDateTime.of(
                                        resultSet.getDate("date").toLocalDate(),
                                        resultSet.getTime("time").toLocalTime()
                                )
                        )
        );
    }
}
