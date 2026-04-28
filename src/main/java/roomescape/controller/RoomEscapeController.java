package roomescape.controller;

import static org.springframework.http.HttpStatus.OK;

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
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.dto.RequestDto.ReservationCreateDto;
import roomescape.dto.ResponseDto;

@RequestMapping("/reservations")
@RestController()
public class RoomEscapeController {

    private final JdbcTemplate jdbcTemplate;

    public RoomEscapeController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping()
    public ResponseEntity<List<ResponseDto.ReservationDto>> findAllReservations() {
        List<ResponseDto.ReservationDto> result = jdbcTemplate.query(
                "SELECT id, name, date, time FROM reservation",
                (rs, rowNum) -> ResponseDto.ReservationDto.of(
                        new Reservation(
                                rs.getLong("id"),
                                rs.getString("name"),
                                LocalDate.parse(rs.getString("date")),
                                LocalTime.parse(rs.getString("time"))
                        )
                )
        );
        return ResponseEntity.ok(result);
    }

    @PostMapping()
    public ResponseEntity<ResponseDto.ReservationDto> createReservation(
            @RequestBody ReservationCreateDto request
    ) {
        Reservation reservation = new Reservation(request.getName(), request.getDate(), request.getTime());

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
        Reservation saved = new Reservation(saveId, reservation.getName(), reservation.getDate(), reservation.getTime());

        return ResponseEntity.ok(ResponseDto.ReservationDto.of(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(
            @PathVariable Long id
    ) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
        return new ResponseEntity<>(OK);
    }
}
