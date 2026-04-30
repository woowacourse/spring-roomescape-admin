package roomescape.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.entity.Reservation;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

@RestController
public class ReservationController {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    ReservationTimeRepository reservationTimeRepository;

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponseDto>> readAll() {
        List<Reservation> reservations = reservationRepository.findAll();
        return ResponseEntity.ok(
                reservations.stream()
                        .map(reservation -> new ReservationResponseDto(
                                reservation.getId(),
                                reservation.getName(),
                                reservation.getDate(),
                                reservation.getTime())
                        )
                        .toList()
        );
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponseDto> create(@RequestBody ReservationRequestDto reservationRequestDto) {
        Reservation reservation = reservationRepository.save(new Reservation(
                reservationRequestDto.name(),
                reservationRequestDto.date(),
                reservationTimeRepository.findById(reservationRequestDto.timeId())
        ));
        return ResponseEntity.ok(new ReservationResponseDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        ));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        reservationRepository.delete(id);
        return ResponseEntity.ok().build();
    }
}
