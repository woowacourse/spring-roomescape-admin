package roomescape.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.repository.ReservationRepository;
import roomescape.model.Reservation;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;

@RestController
@RequestMapping("/reservations")
public final class ReservationApiController {

    private final ReservationRepository reservationRepository;

    public ReservationApiController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping
    public List<ReservationResponseDto> reservations() {
        return reservationRepository.findAll().stream()
                .map(reservation -> new ReservationResponseDto(
                        reservation.getId(),
                        reservation.getName(),
                        reservation.getDate().getStartDate(),
                        reservation.getTime().getStartTime()))
                .toList();
    }

    @PostMapping
    public ReservationResponseDto reserve(@Valid @RequestBody ReservationRequestDto reservationRequestDto) {
        Reservation reservation = reservationRepository.add(new Reservation(
                reservationRequestDto.name(),
                reservationRequestDto.date(),
                reservationRequestDto.time()
        ));
        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate().getStartDate(),
                reservation.getTime().getStartTime());
    }

    @DeleteMapping("/{id}")
    public void cancel(@PathVariable int id) {
        reservationRepository.removeById(id);
    }
}
