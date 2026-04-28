package roomescape.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
        List<Reservation> resultOfFind = reservationService.find();

        List<ReservationResponse> responseData = resultOfFind.stream()
                .map(this::parseReservationToReservationResponse)
                .toList();

        return new ResponseEntity<>(
                responseData,
                HttpStatus.OK
        );
    }

    ResponseEntity<ReservationResponse> addReservation(@RequestBody ReservationRequest request) {
        Reservation result = reservationService.add(
                request.name(),
                request.date(),
                request.time()
        );

        ReservationResponse responseData = parseReservationToReservationResponse(result);

        return new ResponseEntity<>(
                responseData,
                HttpStatus.OK
        );
    }

    private ReservationResponse parseReservationToReservationResponse(Reservation result) {
        return new ReservationResponse(
                result.id(),
                result.name(),
                result.date(),
                result.time()
        );
    }
}
