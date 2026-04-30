package roomescape.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.dto.ReservationRequest;
import roomescape.controller.dto.ReservationResponse;
import roomescape.domain.Reservation;
import roomescape.service.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    ResponseEntity<List<ReservationResponse>> findReservations() {
        List<Reservation> resultOfFind = reservationService.findAll();

        List<ReservationResponse> responseData = resultOfFind.stream()
                .map(ReservationResponse::fromDomain)
                .toList();

        return new ResponseEntity<>(
                responseData,
                HttpStatus.OK
        );
    }

    @PostMapping
    ResponseEntity<ReservationResponse> addReservation(@RequestBody ReservationRequest request) {
        Reservation result = reservationService.save(
                request.name(),
                request.date(),
                request.timeId()
        );

        ReservationResponse responseData = ReservationResponse.fromDomain(result);

        return new ResponseEntity<>(
                responseData,
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
