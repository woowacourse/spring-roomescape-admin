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
        String sql = "SELECT * FROM `reservation`";
        List<Reservation> reservations = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String name = rs.getString("name");
            String date = rs.getString("date");
            String time = rs.getString("time");
            return new Reservation(id, name, date, time);
        });

        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody ReservationRequestDto requestDto) {
        String sql = "INSERT INTO `reservation`(`name`, `date`, `time`) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, requestDto.name());
            preparedStatement.setString(2, requestDto.date());
            preparedStatement.setString(3, requestDto.time());

            return preparedStatement;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();

        Reservation newReservation = new Reservation(id, requestDto.name(), requestDto.date(),
                requestDto.time());

        return ResponseEntity.ok(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        String sql = "DELETE FROM `reservation` WHERE `id` = ?";
        jdbcTemplate.update(sql, id);

        return ResponseEntity.ok().build();
    }
}
