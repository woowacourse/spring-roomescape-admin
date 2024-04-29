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
import roomescape.model.ReservationDto;
import roomescape.model.ReservationRequest;
import roomescape.model.ReservationResponse;
import roomescape.model.ReservationTimeResponse;
import roomescape.service.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> findAll() {
        List<ReservationResponse> allReservations = reservationService.findAllReservations()
                .stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(allReservations);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> save(@RequestBody ReservationRequest reservationRequest) {
        ReservationDto reservationDto = toDto(reservationRequest);
        ReservationDto createdReservation = reservationService.createReservation(reservationDto);
        ReservationResponse reservationResponse = toResponse(createdReservation);
        return ResponseEntity.ok(reservationResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.ok()
                .build();
    }

    private ReservationDto toDto(final ReservationRequest reservationRequest) {
        return new ReservationDto(null, reservationRequest.name(), reservationRequest.date(),
                reservationRequest.timeId(), null);
    }

    private ReservationResponse toResponse(final ReservationDto reservationDto) {
        if (reservationDto.timeId() != null && reservationDto.startAt() != null) {
            return new ReservationResponse(
                    reservationDto.id(),
                    reservationDto.name(),
                    reservationDto.date(),
                    new ReservationTimeResponse(reservationDto.timeId(), reservationDto.startAt())
            );
        }
        throw new RuntimeException(
                "조회한 예약 시간 정보가 불완전합니다. timeId: " + reservationDto.timeId() + "start_at: "
                        + reservationDto.startAt());
    }

}
