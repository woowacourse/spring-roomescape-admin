package roomescape;

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
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<ReservationDto> getReservations(){
        return reservationService.findAll();
    }

    @PostMapping
    public ReservationDto saveReservation(@RequestBody ReservationDto reservation){
        return reservationService.save(reservation.toReservation());
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable long id){
        reservationService.delete(id);
    }

}
