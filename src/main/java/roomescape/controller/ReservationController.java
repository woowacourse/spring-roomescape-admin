package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(0);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationResponseDto> getReservations() {
        List<ReservationResponseDto> responseDtos = new ArrayList<>();

        for (Reservation reservation : reservations) {
            responseDtos.add(ReservationResponseDto.from(reservation));
        }

        return responseDtos;
    }

    @PostMapping
    public ReservationResponseDto addReservation(@RequestBody ReservationRequestDto requestDto) {
        Long id = index.incrementAndGet();
        Reservation reservation = new Reservation(id, requestDto.name(), requestDto.date(), requestDto.time());

        reservations.add(reservation);

        return ReservationResponseDto.from(reservation);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteReservation(@PathVariable("id") Long id) {
        Reservation target = reservations.stream()
                .filter(reservation -> reservation.isEqualId(id))
                .findFirst()
                .orElseThrow();

        reservations.remove(target);
    }
}
