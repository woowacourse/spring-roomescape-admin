package roomescape.admin.reservation.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.admin.reservation.dto.request.ReservationRequest;
import roomescape.admin.reservation.dto.response.ReservationResponse;
import roomescape.admin.reservation.entity.Reservation;
import roomescape.admin.reservation.entity.ReservationTime;
import roomescape.admin.reservation.repository.ReservationRepository;

@RequestMapping("/reservations")
@RestController
public class ReservationController {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> findAll() {
        List<Reservation> reservations = reservationRepository.findAll();
        List<ReservationResponse> reservationResponses = reservations.stream()
                .map(ReservationResponse::from)
                .toList();

        return ResponseEntity.ok(reservationResponses);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> create(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = new Reservation(null, reservationRequest.name(), reservationRequest.date(),
                new ReservationTime(reservationRequest.timeId(), null));
        Reservation saveReservation = reservationRepository.save(reservation);
        return ResponseEntity.ok(ReservationResponse.from(saveReservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        int deleteCount = reservationRepository.delete(id);
        if (deleteCount == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("삭제할 예약이 존재하지 않습니다.");
        }

        return ResponseEntity.noContent().build();
    }
}
