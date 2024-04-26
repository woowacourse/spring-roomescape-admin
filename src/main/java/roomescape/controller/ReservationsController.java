package roomescape.controller;

import org.springframework.web.bind.annotation.*;
import roomescape.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/reservations")
public class ReservationsController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong id = new AtomicLong(1);
    private final ReservationDao reservationDao;

    public ReservationsController(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @GetMapping
    public List<Reservation> read() {
        return reservationDao.findAll();
    }

    @PostMapping
    public ReservationResponse create(@RequestBody ReservationRequest reservationRequest) {
        Reservation newReservation = reservationRequest.toReservation(id.getAndIncrement());
        reservations.add(newReservation);
        return ReservationResponse.toDto(newReservation);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Reservation reservation = reservations.stream()
                .filter(target -> Objects.equals(target.getId(), id))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);

        reservations.remove(reservation);
    }
}
