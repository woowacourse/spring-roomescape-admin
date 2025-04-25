package roomescape.interface_adapter.Reservation;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.usecase.Reservation.AddReservationUseCase;
import roomescape.usecase.Reservation.DeleteReservationUseCase;
import roomescape.usecase.Reservation.GetReservationUseCase;
import roomescape.usecase.Reservation.ReservationOutput;

@RestController
@RequestMapping("/reservations")
public class RoomEscapeController {

    private final GetReservationUseCase getReservationUseCase;
    private final AddReservationUseCase addReservationUseCase;
    private final DeleteReservationUseCase deleteReservationUsecase;

    public RoomEscapeController(GetReservationUseCase getReservationUseCase,
                                AddReservationUseCase addReservationUseCase,
                                DeleteReservationUseCase deleteReservationUsecase) {
        this.getReservationUseCase = getReservationUseCase;
        this.addReservationUseCase = addReservationUseCase;
        this.deleteReservationUsecase = deleteReservationUsecase;
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> addReservation(@RequestBody ReservationRequestDto dto) {
        ReservationOutput output = addReservationUseCase.addReservation(dto.toInput());
        return ResponseEntity.ok(ReservationResponseDto.from(output));
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> getReservations() {
        List<ReservationOutput> outputs = getReservationUseCase.getReservationOutput();
        List<ReservationResponseDto> responses = outputs.stream()
                .map(ReservationResponseDto::from)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
        deleteReservationUsecase.deleteReservation(id);
        return ResponseEntity.ok().build();
    }
}
