package roomescape.reservation;

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
import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.dto.ReservationResponseDto;

import java.sql.PreparedStatement;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final JdbcTemplate jdbcTemplate;

    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> getReservations() {
        List<ReservationResponseDto> reservations =
                jdbcTemplate.query("select * from reservation",
                        (resultSet, rowNum) -> ReservationResponseDto.from(Reservation.of(
                                resultSet.getLong("id"),
                                resultSet.getString("name"),
                                resultSet.getString("date"),
                                resultSet.getString("time")
                        ))
                );
        return ResponseEntity.ok(reservations);
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> createReservation(@RequestBody ReservationRequestDto request) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into reservation(name, date, time) values (?, ?, ?)",
                    new String[]{"id"}
            );
            ps.setString(1, request.name());
            ps.setString(2, request.date());
            ps.setString(3, request.time());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        return ResponseEntity.ok().body(ReservationResponseDto.from(Reservation.of(id, request.name(), request.date(), request.time())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        jdbcTemplate.update("delete from reservation where id = ?", id);
        return ResponseEntity.ok().build();
    }
}
