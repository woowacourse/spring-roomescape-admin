package roomescape;

import java.sql.PreparedStatement;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class TimeController {

    private final JdbcTemplate jdbcTemplate;

    public TimeController(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @PostMapping("/times")
    public ResponseEntity<ReservationTimeResponseDto> addReservationTime(
            @RequestBody ReservationTimeRequestDto reservationTimeRequestDto) {
        String sql = "insert into reservation_time(start_at) values(?) ";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((connection) -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, String.valueOf(reservationTimeRequestDto.time()));
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        ReservationTimeResponseDto reservationTimeResponseDto = new ReservationTimeResponseDto(id,
                reservationTimeRequestDto.time());
        return ResponseEntity.ok(reservationTimeResponseDto);

    }
}
