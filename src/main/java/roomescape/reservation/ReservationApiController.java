package roomescape.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

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
        String sql = "SELECT id, name, date, time FROM reservation";
        final List<Reservation> reservations = jdbcTemplate.query(sql, (resultSet, rowNumber) ->
                new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        LocalDate.parse(resultSet.getString("date")),
                        LocalTime.parse(resultSet.getString("time"))));
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody final ReservationDto reservationDto) {
        String sql = "INSERT INTO reservation(name,date,time) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, reservationDto.getName(), reservationDto.getDate(), reservationDto.getTime());
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservationDto.getName());
            ps.setString(2, String.valueOf(reservationDto.getDate()));
            ps.setString(3, String.valueOf(reservationDto.getTime()));
            return ps;
        }, keyHolder);
        Long id = keyHolder.getKey().longValue();
        Reservation newReservation = reservationDto.toVo(id);
        return ResponseEntity.ok().body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
        return ResponseEntity.ok().build();
    }
}
