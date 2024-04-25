package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import roomescape.Reservation;
import roomescape.ReservationDto;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final AtomicLong index = new AtomicLong(1);

    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping
    public List<ReservationDto> findAll() {
        return reservations.stream()
                .map(ReservationDto::from)
                .toList();
    }

    @PostMapping
    public ReservationDto create(@RequestBody ReservationDto reservationDto) {
        Reservation reservation = reservationDto.toEntity(index.getAndIncrement());
        reservations.add(reservation);
        return ReservationDto.from(reservation);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        reservations.removeIf(reservation -> reservation.getId() == id);
    }
}
