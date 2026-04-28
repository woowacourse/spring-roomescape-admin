package roomescape.controller;

import java.sql.PreparedStatement;
import java.time.format.DateTimeFormatter;
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
import roomescape.dto.ReservationTimeCreateDto;
import roomescape.dto.ReservationTimeDto;

@RequiredArgsConstructor
@RequestMapping("/times")
@RestController()
public class ReservationTimeController {

    private final JdbcTemplate jdbcTemplate;

    @GetMapping
    public ResponseEntity<List<ReservationTimeDto>> findAllReservationTimes() {
        List<ReservationTimeDto> result = jdbcTemplate.query(
                "SELECT id, start_at FROM reservation_time",
                (rs, rowNum) -> ReservationTimeDto.from(
                        ReservationTime.builder()
                                .id(rs.getLong("id"))
                                .startAt(rs.getTime("start_at").toLocalTime())
                                .build()
                )
        );
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<ReservationTimeDto> createReservationTime(@RequestBody ReservationTimeCreateDto request) {
        ReservationTime reservationTime = request.toEntity();
        String formattedStartAt = reservationTime.getStartAt().format(DateTimeFormatter.ofPattern("HH:mm"));

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO reservation_time (start_at) VALUES (?)",
                    new String[]{"id"});
            ps.setString(1, formattedStartAt);
            return ps;
        }, keyHolder);

        Long saveId = keyHolder.getKey().longValue();

        ReservationTime saved = ReservationTime.builder()
                .id(saveId)
                .startAt(reservationTime.getStartAt())
                .build();

        return ResponseEntity.ok(ReservationTimeDto.from(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id) {
        jdbcTemplate.update("DELETE FROM reservation_time WHERE id = ?", id);
        return ResponseEntity.ok().build();
    }
}
