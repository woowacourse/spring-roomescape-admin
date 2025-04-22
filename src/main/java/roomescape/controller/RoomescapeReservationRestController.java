package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import roomescape.dao.ReservationDAO;
import roomescape.entity.ReservationEntity;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class RoomescapeReservationRestController {
    private final ReservationDAO reservationDAO;

    public RoomescapeReservationRestController(JdbcTemplate jdbcTemplate) {
        this.reservationDAO = new ReservationDAO(jdbcTemplate);
    }

    @GetMapping
    public List<ReservationResponseDto> getAllReservation() {
        return reservationDAO.findAll()
                .stream()
                .map(ReservationResponseDto::from)
                .toList();
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> createReservation(@RequestBody ReservationRequestDto request) {
        ReservationEntity entity = request.toEntity();
        try {
            ReservationEntity saved = reservationDAO.save(entity);
            return ResponseEntity.ok().body(ReservationResponseDto.from(saved));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        try {
            reservationDAO.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
