package roomescape.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.entity.ReservationWithTimeId;
import roomescape.model.Reservation;
import roomescape.service.ReservationService;

@RequestMapping("/reservations")
@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> reservations() {

        List<Reservation> reservations = reservationService.selectAllReservation();

        List<ReservationResponse> reservationResponses = reservations.stream()
                .map(ReservationResponse::toDto).toList();

        return ResponseEntity.ok().body(reservationResponses);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> addReservation(final @RequestBody @Valid ReservationRequest request) {

        ReservationWithTimeId reservationWithTimeId = request.toReservationWithId();
        Reservation addedReservation;

        try {
            addedReservation = reservationService.addReservation(reservationWithTimeId);
        } catch (IllegalArgumentException e) {

            return ResponseEntity.noContent().build();
        }

        ReservationResponse reservationResponse = ReservationResponse.toDto(addedReservation);

        return ResponseEntity.ok().body(reservationResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(final @PathVariable("id") Long id) {

        int effectedRow = reservationService.deleteReservationById(id);

        if (effectedRow == 1) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidateDtoException(MethodArgumentNotValidException exception) {
        return ResponseEntity.badRequest().body(exception.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
    }
}
