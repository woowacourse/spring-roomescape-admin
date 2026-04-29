package roomescape;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReservationController {

    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/reservations")
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @PostMapping("/reservations")
    public Reservation addReservation(@RequestBody ReservationSaveDto reservationSaveDto) {
        return reservationRepository.save(
                reservationSaveDto.name(),
                reservationSaveDto.date(),
                reservationSaveDto.time()
        );
    }

    @DeleteMapping("/reservations/{id}")
    public void deleteReservation(@PathVariable Long id) {
        reservationRepository.deleteById(id);
    }

}
