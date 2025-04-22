package roomescape.admin.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.admin.controller.dto.ReservationTimeRequest;
import roomescape.admin.controller.dto.ReservationTimeResponse;
import roomescape.admin.domain.ReservationTime;
import roomescape.admin.repository.time.ReservationTimeRepository;

@RestController
@RequestMapping("/times")
public class ReservationTimeRestController {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeRestController(
            @Qualifier("h2ReservationTimeRepository") final ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> persistReservationTime(
            @RequestBody final ReservationTimeRequest request
    ) {
        final Long id = reservationTimeRepository.save(request.toReservationTime());
        final ReservationTime found = reservationTimeRepository.getOneById(id);
        return ResponseEntity.ok(ReservationTimeResponse.from(found));
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> retrieveReservationTimes() {
        final List<ReservationTime> founds = reservationTimeRepository.findAll();
        final List<ReservationTimeResponse> reservationTimeResponses = founds.stream()
                .map(ReservationTimeResponse::from)
                .toList();

        return ResponseEntity.ok(reservationTimeResponses);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteReservationTime(
            @PathVariable final Long id
    ) {
        final ReservationTime found = reservationTimeRepository.getOneById(id);

        reservationTimeRepository.delete(found);

        return ResponseEntity.ok().build();
    }
}
