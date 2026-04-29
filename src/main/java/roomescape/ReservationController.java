package roomescape;

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
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {
    private final JdbcTemplate jdbcTemplate;

    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> readAll() {
        String sql = "SELECT r.id, r.name, r.date, t.id as time_id, t.start_at as time_value FROM `reservation` r INNER JOIN `reservation_time` t ON r.time_id = t.id";
        List<Reservation> reservations = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String name = rs.getString("name");
            String date = rs.getString("date");
            Long timeId = rs.getLong("time_id");
            String timeValue = rs.getString("time_value");

            ReservationTime reservationTime = new ReservationTime(timeId, timeValue);
            return new Reservation(id, name, date, reservationTime);
        });

        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody ReservationRequestDto requestDto) {
        String reservationSql = "INSERT INTO `reservation`(`name`, `date`, `time_id`) VALUES (?, ?, ?)";
        String reservationTimeSql = "SELECT * from `reservation_time` WHERE `id` = ?";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(reservationSql, new String[]{"id"});
            preparedStatement.setString(1, requestDto.name());
            preparedStatement.setString(2, requestDto.date());
            preparedStatement.setLong(3, requestDto.timeId());

            return preparedStatement;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        ReservationTime reservationTime = jdbcTemplate.queryForObject(reservationTimeSql,
                (rs, rowNum) -> new ReservationTime(rs.getLong("id"), rs.getString("start_at")), requestDto.timeId());
        Reservation newReservation = new Reservation(id, requestDto.name(), requestDto.date(), reservationTime);

        return ResponseEntity.ok(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        String sql = "DELETE FROM `reservation` WHERE `id` = ?";
        jdbcTemplate.update(sql, id);

        return ResponseEntity.ok().build();
    }
}
