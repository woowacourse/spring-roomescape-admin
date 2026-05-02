package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.repository.ReservationJoinedDto;
import roomescape.service.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> reservations() {
        return ResponseEntity.ok(convertToReservationResponse(reservationService.allReservations()));
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest reservationRequest) {
        ReservationJoinedDto reservation = reservationService.saveReservation(reservationRequest.name(),
                reservationRequest.date(), reservationRequest.timeId());
        ReservationResponse reservationResponse = toResponse(reservation);
        return ResponseEntity.ok(reservationResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@PathVariable long id) {
        reservationService.removeReservation(id);
        return ResponseEntity.ok().build();
    }

    private List<ReservationResponse> convertToReservationResponse(List<ReservationJoinedDto> reservations) {
        return reservations.stream()
                .map(this::toResponse)
                .toList();
    }

    private ReservationResponse toResponse(ReservationJoinedDto reservationJoinedDto) {
        ReservationTimeResponse reservationTimeResponse = new ReservationTimeResponse(
                reservationJoinedDto.timeId(),
                reservationJoinedDto.startAt()
        );
        return new ReservationResponse(
                reservationJoinedDto.id(),
                reservationJoinedDto.name(),
                reservationJoinedDto.date(),
                reservationTimeResponse
        );
    }
}
