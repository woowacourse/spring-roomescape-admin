package roomescape;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.RequestReservation;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/reservations")
public class ReservationsController {

    @Autowired
    private ReservationRepository reservationRepository;

    @GetMapping
    @ResponseBody
    public List<Reservation> reservations() {
        return reservationRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Reservation> addReservationInfo(@RequestBody RequestReservation requestReservation) {
        Long id = reservationRepository.add(requestReservation);
        return ResponseEntity.created(URI.create("/reservations/" + id))
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservationInfo(@PathVariable Long id) {
        reservationRepository.remove(id);
    }
}
