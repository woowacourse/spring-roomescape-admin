package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.dto.ReservationDto;
import roomescape.service.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<ReservationDto> getReservations(){
        return reservationService.findAll()
                .stream()
                .map(ReservationDto::from)
                .toList();
    }

    @GetMapping("/{id}")
    public ReservationDto getReservationById(@RequestParam long id){
        return ReservationDto.from(reservationService.findById(id));
    }

    @PostMapping
    public ReservationDto saveReservation(@RequestBody ReservationDto reservation){
        return ReservationDto.from(reservationService.save(reservation.toReservation()));
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable long id){
        reservationService.delete(id);
    }

}
