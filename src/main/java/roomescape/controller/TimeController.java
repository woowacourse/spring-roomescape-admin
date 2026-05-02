package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.ReservationTime;
import roomescape.service.ReservationTimeService;

@RestController
public class TimeController {

    private final ReservationTimeService reservationTimeService;

    public TimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping("/times")
    public List<ReservationTime> getReservationTime(){
        return reservationTimeService.findAll();
   }

    @PostMapping("/times")
    public ReservationTime postReservationTime(@RequestBody ReservationTime reservationTime){
        return reservationTimeService.save(reservationTime);
    }

    @DeleteMapping("/times/{id}")
    public void deleteReservationTime(@PathVariable Long id){
        reservationTimeService.deleteById(id);
    }
}
