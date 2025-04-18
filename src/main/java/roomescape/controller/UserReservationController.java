package roomescape.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRepository;
import roomescape.controller.dto.request.ReservationCreateRequest;
import roomescape.controller.dto.response.ReservationResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class UserReservationController {

    private final ReservationRepository reservationRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<ReservationResponse> getAll() {
        return reservationRepository.getAll().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ReservationResponse create(@RequestBody ReservationCreateRequest request) {
        Reservation reservation = request.toDomain();

        Reservation savedReservation = reservationRepository.save(reservation);

        return ReservationResponse.from(savedReservation);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        Reservation target = getReservation(id);

        reservationRepository.remove(target.getId());
    }

    private Reservation getReservation(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("id에 해당하는 예약이 존재하지 않습니다."));
    }
}
