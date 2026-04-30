package roomescape.reservation;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @PostMapping
    public Reservation create(@RequestBody Reservation reservation) {
        Long id = reservationRepository.insert(reservation);
        return Reservation.toEntity(reservation, id);
    }

    @GetMapping
    public List<Reservation> read() {
        return reservationRepository.findAllReservations();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        reservationRepository.delete(id);
    }
}
