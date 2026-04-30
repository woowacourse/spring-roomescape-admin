package roomescape.controller;

import java.sql.PreparedStatement;
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
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final JdbcTemplate jdbcTemplate;

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> getAllReservation() {
        String sql = "SELECT * FROM reservation";
        List<ReservationResponseDto> responseDtoList = jdbcTemplate.query(sql,(resultSet, rowNum) ->
                new ReservationResponseDto(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getObject("date",LocalDate.class),
                        resultSet.getObject("time", LocalTime.class)
                ));
        return ResponseEntity.ok(responseDtoList);
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> createReservation(@RequestBody ReservationRequestDto reservationRequestDto) {
        String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservationRequestDto.name());
            ps.setObject(2, reservationRequestDto.date());
            ps.setObject(3, reservationRequestDto.time());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();

        Reservation newReservation = reservationRequestDto.toEntity(id);
        return ResponseEntity.ok(ReservationResponseDto.from(newReservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        int affectedRows = jdbcTemplate.update(sql, id);

        if (affectedRows == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

}
