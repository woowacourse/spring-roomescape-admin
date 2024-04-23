package roomescape.reservationtime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;

@RestController
public class ReservationTimeApiController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @PostMapping("/times")
    ResponseEntity<ReservationTime> create(@RequestBody final ReservationTimeRequest reservationTimeRequest) {
        String sql = "INSERT INTO reservation_time (start_at) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, String.valueOf(reservationTimeRequest.getStartAt()));
            return ps;
        }, keyHolder);
        Long id = keyHolder.getKey().longValue();
        ReservationTime newReservationTime = new ReservationTime(id, reservationTimeRequest.getStartAt());
        return ResponseEntity.ok().body(newReservationTime);
    }

    @GetMapping("/times")
    ResponseEntity<List<ReservationTime>> findAll() {
        String sql = "SELECT id, start_at FROM reservation_time";
        List<ReservationTime> reservationTimes = jdbcTemplate.query(sql, (resultSet, rowNumber) ->
                new ReservationTime(
                        resultSet.getLong("id"),
                        LocalTime.parse(resultSet.getString("start_at"))));
        return ResponseEntity.ok().body(reservationTimes);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, id);
        return ResponseEntity.noContent().build();
    }
}