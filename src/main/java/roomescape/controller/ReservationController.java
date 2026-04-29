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
import roomescape.domain.Reservation;
import roomescape.domain.ReservationCommand;
import roomescape.dto.ReservationResponse;
import roomescape.dto.AddReservationRequest;
import roomescape.service.RoomReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final RoomReservationService roomReservationService;

    public ReservationController(RoomReservationService roomReservationService) {
        this.roomReservationService = roomReservationService;
    }

    @GetMapping()
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        List<Reservation> reservations = roomReservationService.getAllReservation();
        List<ReservationResponse> response = reservations.stream().map(ReservationResponse::from).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody AddReservationRequest addReservationRequest) {
        ReservationCommand reservationCommand = addReservationRequest.to();
        Reservation addedReservation = roomReservationService.addReservation(reservationCommand);
        ReservationResponse response = ReservationResponse.from(addedReservation);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") long id) {
        int deletedCount = roomReservationService.deleteReservation(id);

        if(deletedCount == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
