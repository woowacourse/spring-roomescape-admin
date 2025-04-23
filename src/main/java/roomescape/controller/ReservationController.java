package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

@Controller
@RequestMapping("reservations")
public class ReservationController {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository timeRepository;

    public ReservationController(ReservationRepository reservationRepository,
                                 ReservationTimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        List<ReservationResponse> response = ReservationResponse.from(reservationRepository.findAll());
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest request) {
        ReservationTime time = timeRepository.findBy(request.getTimeId());
        Reservation newReservation = new Reservation(request.getName(), request.getDate(), time);
        ReservationResponse response = ReservationResponse.from(reservationRepository.add(newReservation));
        return ResponseEntity.created(URI.create("reservations/" + response.getId())).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationRepository.deleteBy(id);
        return ResponseEntity.ok().build();
    }
}
