package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.entity.Reservation;
import roomescape.service.ReservationService;

import java.util.List;

@RequestMapping("/reservations")
@RestController
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

//    @PostMapping
//    public void createReservation(@RequestBody ReservationDto reservationDto) {
//        reservationService.addReservation(reservationDto);
//    }

//    @DeleteMapping("/{id}")
//    public void deleteReservationById(@PathVariable("id") long id) {
//        Reservation findReservation = reservations.stream()
//                .filter(reservation -> reservation.getId() == id)
//                .findAny()
//                .orElseThrow(() -> new NoSuchElementException("id에 해당하는 예약을 찾을 수 없습니다: " + id));
//        reservations.remove(findReservation);
//    }
}
