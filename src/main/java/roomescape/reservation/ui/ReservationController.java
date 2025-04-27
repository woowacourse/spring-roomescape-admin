package roomescape.reservation.ui;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.common.uri.UriFactory;
import roomescape.reservation.application.ReservationService;
import roomescape.reservation.domain.ReservationId;
import roomescape.reservation.ui.dto.ReservationRequestDto;
import roomescape.reservation.ui.dto.ReservationResponseDto;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(ReservationController.BASE_PATH)
public class ReservationController {

    public static final String BASE_PATH = "/reservations";

    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> getAll() {
        final List<ReservationResponseDto> reservations = reservationService.getAll();
        return ResponseEntity.ok(reservations);
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> create(
            @RequestBody @Valid final ReservationRequestDto reservationRequestDto) {
        final ReservationResponseDto reservationResponseDto = reservationService.create(reservationRequestDto);
        final URI location = UriFactory.buildPath(BASE_PATH, String.valueOf(reservationResponseDto.id()));
        return ResponseEntity.created(location)
                .body(reservationResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final long id) {
        reservationService.delete(ReservationId.from(id));
        return ResponseEntity.noContent().build();
    }
}
