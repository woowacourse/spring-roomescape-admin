package roomescape;

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
            ps.setString(1, String.valueOf(reservationTimeRequestDto.startAt()));
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        ReservationTimeResponseDto reservationTimeResponseDto = new ReservationTimeResponseDto(id,
                reservationTimeRequestDto.startAt());
        return ResponseEntity.ok(reservationTimeResponseDto);

    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTimeResponseDto>> getReservationTime() {
        String sql = "select * from reservation_time";
        List<ReservationTimeResponseDto> reservationTimeResponseDtos = jdbcTemplate.query(sql, (resultSet, rowNUm) -> {
            return new ReservationTimeResponseDto(resultSet.getLong("id"),
                    LocalTime.parse(resultSet.getString("start_at")));
        });
        return ResponseEntity.ok(reservationTimeResponseDtos);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable long id) {
        String sql = "delete from reservation_time where id = ?";
        jdbcTemplate.update(sql, id);
        return ResponseEntity.ok().build();
    }

}
