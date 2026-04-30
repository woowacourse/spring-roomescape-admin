package roomescape.controller;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.dto.ReservationTimeResponseDto;

@RestController
@RequestMapping("/times")
@RequiredArgsConstructor
public class ReservationTimeController {

    private final JdbcTemplate jdbcTemplate;

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponseDto>> getAllReservationTime() {
        String sql = "SELECT * FROM reservation_time";
        List<ReservationTimeResponseDto> responseDtoList = jdbcTemplate.query(sql,(resultSet, rowNum) ->
                new ReservationTimeResponseDto(
                        resultSet.getLong("id"),
                        resultSet.getObject("start_at", LocalTime.class)
                ));
        return ResponseEntity.ok(responseDtoList);

    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponseDto> createReservationTime(@RequestBody ReservationTimeRequestDto reservationTimeRequestDto) {
        String sql = "INSERT INTO reservation_time (start_at) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setObject(1, reservationTimeRequestDto.startAt());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();

        ReservationTime newReservationTime = reservationTimeRequestDto.toEntity(id);
        return ResponseEntity.ok(ReservationTimeResponseDto.from(newReservationTime));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        int affectedRows = jdbcTemplate.update(sql, id);

        if (affectedRows == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

}
