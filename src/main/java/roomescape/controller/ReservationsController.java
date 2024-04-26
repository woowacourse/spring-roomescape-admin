package roomescape.controller;

import org.springframework.web.bind.annotation.*;
import roomescape.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationsController {
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
    public Reservation create(@RequestBody ReservationRequest reservationRequest) {
        return reservationDao.insert(reservationRequest);
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
