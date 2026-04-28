package roomescape.controller;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @GetMapping
    public String getAllReservation() {
        return "temp";
    }

    @PostMapping
    public String createReservation() {
        return "temp";
    }

    @DeleteMapping("/{id}")
    public String deleteReservation(@PathVariable Long id) {
        return "temp";
    }


}
