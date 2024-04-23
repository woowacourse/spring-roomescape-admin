package roomescape.reservationtime.controller;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservationtime.controller.request.CreateReservationTimeRequest;
import roomescape.reservationtime.repository.ReservationTimeRepository;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeController(final ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @PostMapping
    public ResponseEntity<Void> createReservationTime(@RequestBody final CreateReservationTimeRequest createReservationTimeRequest) {
        Long id = reservationTimeRepository.save(createReservationTimeRequest.toDomain());
        return ResponseEntity.created(URI.create("/times/" + id)).build();
    }
}
