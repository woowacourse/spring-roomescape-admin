package roomescape.reservationtime.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservationtime.controller.request.CreateReservationTimeRequest;
import roomescape.reservationtime.controller.response.FindReservationTimeResponse;
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

    @GetMapping
    public ResponseEntity<List<FindReservationTimeResponse>> getReservationTimes() {
        List<FindReservationTimeResponse> reservationTimeResponses = reservationTimeRepository.findAll().stream()
                .map(FindReservationTimeResponse::of)
                .toList();

        return ResponseEntity.ok(reservationTimeResponses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable final Long id) {
        reservationTimeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
