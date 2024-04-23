package roomescape.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;
import roomescape.reservationtime.ReservationTime;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
public class ReservationApiController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> findAll() {
        String sql = "SELECT r.id as reservation_id, \n" +
                "    r.name, \n" +
                "    r.date, \n" +
                "    t.id as time_id, \n" +
                "    t.start_at as time_value \n" +
                "FROM reservation as r \n" +
                "inner join reservation_time as t \n" +
                "on r.time_id = t.id";
        final List<Reservation> reservations = jdbcTemplate.query(sql, (resultSet, rowNumber) ->
                new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        LocalDate.parse(resultSet.getString("date")),
                        new ReservationTime(resultSet.getLong("time_id"),
                                LocalTime.parse(resultSet.getString("time_value")))));
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody final ReservationDto reservationDto) {
        String sql = "INSERT INTO reservation(name,date,time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservationDto.getName());
            ps.setString(2, String.valueOf(reservationDto.getDate()));
            ps.setLong(3, reservationDto.getTimeId());
            return ps;
        }, keyHolder);
        Long id = keyHolder.getKey().longValue();
        Long time_id = reservationDto.getTimeId();
        String sql2 = "SELECT start_at FROM reservation_time WHERE id = ?";
        final ReservationTime reservationTime = jdbcTemplate.queryForObject(sql2, (resultSet, rowNumber) ->
                new ReservationTime(time_id, LocalTime.parse(resultSet.getString("start_at"))), time_id);
        Reservation newReservation = new Reservation(id, reservationDto.getName(), reservationDto.getDate(), reservationTime);
        return ResponseEntity.ok().body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
        return ResponseEntity.noContent().build();
    }
}
