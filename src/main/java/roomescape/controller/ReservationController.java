package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.repository.ReservationRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationResponseDto> getReservations() {
        List<ReservationResponseDto> responseDtos = new ArrayList<>();
        List<Reservation> reservations = reservationRepository.findAll();

        for (Reservation reservation : reservations) {
            responseDtos.add(ReservationResponseDto.from(reservation));
        }

        return responseDtos;
    }

    @PostMapping
    public ReservationResponseDto addReservation(@RequestBody ReservationRequestDto requestDto) {
        Reservation reservation = new Reservation(null, requestDto.name(), requestDto.date(), requestDto.time());

        reservationRepository.createReservation(reservation);

        return ReservationResponseDto.from(reservation);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteReservation(@PathVariable("id") Long id) {
        reservationRepository.deleteById(id);
    }
}
