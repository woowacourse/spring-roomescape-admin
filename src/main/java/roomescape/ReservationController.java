package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.CreateReservationRequest;
import roomescape.dto.CreateReservationResponse;

@RequestMapping("/reservations")
@RestController
public class ReservationController {
    Reservations reservations = new Reservations();

    @PostMapping
    public ResponseEntity<CreateReservationResponse> createReservation(@RequestBody CreateReservationRequest createReservationRequest){
        Reservation reservation = new Reservation(
                createReservationRequest.name(),
                DateAndTimeConverter.parseToDate(createReservationRequest.date()),
                DateAndTimeConverter.parseToTime(createReservationRequest.time())
        );

        CreateReservationResponse createReservationResponse = new CreateReservationResponse(
                reservations.add(reservation),
                reservation.getName(),
                DateAndTimeConverter.formatDate(reservation.getDate()),
                DateAndTimeConverter.formatTime(reservation.getTime())
        );

        return ResponseEntity.ok(createReservationResponse);
    }
}
