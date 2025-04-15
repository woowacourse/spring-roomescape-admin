package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.entity.Reservation;
import roomescape.dao.MemoryReservationDao;
import roomescape.dto.CreateReservationDto;
import roomescape.dto.ReservationDto;
import roomescape.repository.ReservationRepository;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationRepository reservationRepository = new MemoryReservationDao();

    @GetMapping
    public ResponseEntity<List<ReservationDto>> getAllReservations() {
        List<ReservationDto> reservationDtos = reservationRepository.getReservations().stream().map(ReservationDto::from).toList();
        return ResponseEntity.ok(reservationDtos);
    }

    @PostMapping
    public ResponseEntity<ReservationDto> createReservation(@RequestBody CreateReservationDto createReservationDto) {
        Reservation entity = createReservationDto.toEntity();
        Reservation savedReservation = reservationRepository.add(entity);
        return ResponseEntity.ok(ReservationDto.from(savedReservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        if (!reservationRepository.existReservation(id)) {
            throw new IllegalArgumentException("존재하지 않는 예약입니다.");
        }
        reservationRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
