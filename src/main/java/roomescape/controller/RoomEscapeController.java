package roomescape.controller;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationCreateDto;
import roomescape.dto.ReservationDto;

@RequiredArgsConstructor
@RequestMapping("/reservations")
@RestController()
public class RoomEscapeController {

    private final JdbcTemplate jdbcTemplate;

    @GetMapping()
    public ResponseEntity<List<ReservationDto>> findAllReservations() {
        List<ReservationDto> result = jdbcTemplate.query(
                "SELECT id, name, date, time FROM reservation",
                (rs, rowNum) -> ReservationDto.from(
                        Reservation.builder()
                                .id(rs.getLong("id"))
                                .name(rs.getString("name"))
                                .date(LocalDate.parse(rs.getString("date")))
                                .time(LocalTime.parse(rs.getString("time")))
                                .build()
                )
        );
        return ResponseEntity.ok(result);
    }

    @PostMapping()
    public ResponseEntity<ReservationDto> createReservation(
            @RequestBody ReservationCreateDto request
    ) {
        Reservation reservation = request.toEntity();

        String formattedDate = reservation.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String formattedTime = reservation.getTime().format(DateTimeFormatter.ofPattern("HH:mm"));

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)",
                    new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, formattedDate);
            ps.setString(3, formattedTime);
            return ps;
        }, keyHolder);

        Long saveId = keyHolder.getKey().longValue();
        Reservation saved = Reservation.builder()
                .id(saveId)
                .name(reservation.getName())
                .date(reservation.getDate())
                .time(reservation.getTime())
                .build();

        return ResponseEntity.ok(ReservationDto.from(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(
            @PathVariable Long id
    ) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
        return ResponseEntity.ok().build();
    }
}
