package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dao.ReservationDAO;
import roomescape.dao.TimeDao;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.entity.ReservationEntity;
import roomescape.entity.ReservationTimeEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservations")
public class RoomescapeReservationRestController {
    private final ReservationDAO reservationDAO;
    private final TimeDao timeDao;

    public RoomescapeReservationRestController(ReservationDAO reservationDAO, TimeDao timeDao) {
        this.reservationDAO = reservationDAO;
        this.timeDao = timeDao;
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
        Optional<ReservationTimeEntity> timeEntity = timeDao.findById(request.timeId());
        if (timeEntity.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            ReservationEntity saved = reservationDAO.save(request.toEntity(timeEntity.get()));
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
