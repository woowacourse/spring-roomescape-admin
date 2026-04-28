package roomescape;

import org.springframework.web.bind.annotation.*;

import java.util.List;

public class ReservationController {

    private final ReservationRepository reservationRepository;

    public ReservationController() {
        this.reservationRepository = new ReservationRepository();
    }

    @GetMapping("/reservations")
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @PostMapping("/reservations")
    public Reservation addReservation(@RequestBody ReservationSaveDto reservationSaveDto) {
        return reservationRepository.save(
                reservationSaveDto.name(),
                reservationSaveDto.reservationDate(),
                reservationSaveDto.reservationTime()
        );
    }

    @DeleteMapping("/reservations/{id}")
    public void deleteReservation(@PathVariable Long id) {
        reservationRepository.deleteById(id);
    }

}
