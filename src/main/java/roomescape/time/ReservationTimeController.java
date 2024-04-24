package roomescape.time;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.PreparedStatement;
import java.sql.Time;

@RestController
public class ReservationTimeController {
    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeController(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> create(@RequestBody ReservationTimeRequest reservationTimeRequest) {
        final String sql = "INSERT INTO reservation_time(start_at) VALUES(?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            final PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setTime(1, Time.valueOf(reservationTimeRequest.startAt()));
            return preparedStatement;
        }, keyHolder);

        final long id = keyHolder.getKey().longValue();
        final ReservationTime reservationTime = new ReservationTime(id, reservationTimeRequest.startAt());

        return ResponseEntity.ok(reservationTime);
    }
}
