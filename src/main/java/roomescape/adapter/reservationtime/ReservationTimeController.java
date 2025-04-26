package roomescape.adapter.reservationtime;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.usecase.reservationTime.AddReservationTimeUseCase;
import roomescape.usecase.reservationTime.DeleteReservationTimeUsecase;
import roomescape.usecase.reservationTime.GetReservationTimeUseCase;
import roomescape.usecase.reservationTime.ReservationTimeOutput;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final AddReservationTimeUseCase addUseCase;
    private final GetReservationTimeUseCase getUseCase;
    private final DeleteReservationTimeUsecase deleteUseCase;

    public ReservationTimeController(
            AddReservationTimeUseCase addUseCase,
            GetReservationTimeUseCase getUseCase,
            DeleteReservationTimeUsecase deleteUseCase
    ) {
        this.addUseCase = addUseCase;
        this.getUseCase = getUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponseDto> add(@RequestBody ReservationTimeRequestDto request) {
        ReservationTimeOutput output = addUseCase.addReservationTime(request.startAt());
        return ResponseEntity.ok(ReservationTimeResponseDto.from(output));
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponseDto>> getAll() {
        List<ReservationTimeOutput> outputs = getUseCase.getAllReservationTimes();
        List<ReservationTimeResponseDto> responseList = outputs.stream()
                .map(ReservationTimeResponseDto::from)
                .toList();
        return ResponseEntity.ok(responseList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        deleteUseCase.deleteReservationTime(id);
        return ResponseEntity.ok().build();
    }
}
