package roomescape.controller;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@RestController
@RequestMapping("/reservations")
public class RoomescapeApiController {

    private final JdbcTemplate jdbcTemplate;

    public RoomescapeApiController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> reserve(@RequestBody ReservationRequest request) {
        ReservationTime time = jdbcTemplate.queryForObject(
                "select * from reservation_time where id = ?",
                (resultSet, rowNum) -> new ReservationTime(
                        resultSet.getLong("id"),
                        resultSet.getTime("start_at").toLocalTime()
                ), request.timeId());
        Reservation reservation = new Reservation(request.name(), request.date(), time);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into reservation (name, date, time_id) values (?, ?, ?)", new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString());
            ps.setLong(3, reservation.getTime().getId());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        Reservation saved = new Reservation(id, reservation.getName(), reservation.getDate(), reservation.getTime());
        return ResponseEntity.ok(ReservationResponse.from(saved));
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {
        List<Reservation> reservations = jdbcTemplate.query("""
                               SELECT r.id AS reservation_id, 
                                      r.name,
                                      r.date,
                                      t.id AS time_id,
                                      t.start_at
                               FROM reservation as r
                               INNER JOIN reservation_time as t
                               ON r.time_id = t.id
                               """,
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("reservation_id"),
                        resultSet.getString("name"),
                        resultSet.getDate("date").toLocalDate(),
                        new ReservationTime(
                                resultSet.getLong("time_id"),
                                resultSet.getTime("start_at").toLocalTime()
                        )));
        List<ReservationResponse> response = reservations.stream()
                .map(ReservationResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        jdbcTemplate.update("delete from reservation where id = ?", id);
        return ResponseEntity.ok().build();
    }
}
