package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dao.ReservationDAO;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.entity.ReservationEntity;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class RoomescapeReservationRestController {
    private final ReservationDAO reservationDAO;

    public RoomescapeReservationRestController(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
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
