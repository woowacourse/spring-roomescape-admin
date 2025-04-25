package roomescape.interface_adapter.ReservationTime;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.usecase.ReservationTime.AddReservationTimeUseCase;
import roomescape.usecase.ReservationTime.DeleteReservationTimeUsecase;
import roomescape.usecase.ReservationTime.GetReservationTimeUseCase;
import roomescape.usecase.ReservationTime.ReservationTimeOutput;

@Controller
public class ReservationTimeController {


    private final AddReservationTimeUseCase addReservationTimeUseCase;
    private final GetReservationTimeUseCase getReservationTimeUseCase;
    private final DeleteReservationTimeUsecase deleteReservationTimeUsecase;

    public ReservationTimeController(final AddReservationTimeUseCase addReservationTimeUseCase,
                                     final GetReservationTimeUseCase getReservationTimeUseCase,
                                     final DeleteReservationTimeUsecase deleteReservationTimeUsecase) {
        this.addReservationTimeUseCase = addReservationTimeUseCase;
        this.getReservationTimeUseCase = getReservationTimeUseCase;
        this.deleteReservationTimeUsecase = deleteReservationTimeUsecase;
    }


    @PostMapping("/times")
    public ResponseEntity<ReservationTimeResponseDto> addReservationTime(
            @RequestBody ReservationTimeRequestDto reservationTimeRequestDto) {

        ReservationTimeOutput reservationTimeOutput = addReservationTimeUseCase.addReservationTime(
                reservationTimeRequestDto.startAt());
        ReservationTimeResponseDto reservationTimeResponseDto = ReservationTimeResponseDto.from(
                reservationTimeOutput);
        return ResponseEntity.ok(reservationTimeResponseDto);

    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTimeResponseDto>> getReservationTime() {
        List<ReservationTimeOutput> reservationTimeOutputs = getReservationTimeUseCase.getAllReservationTimes();
        List<ReservationTimeResponseDto> reservationTimeResponseDtos = reservationTimeOutputs.stream()
                .map(ReservationTimeResponseDto::from)
                .toList();
        return ResponseEntity.ok(reservationTimeResponseDtos);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable long id) {
        deleteReservationTimeUsecase.deleteReservationTime(id);
        return ResponseEntity.ok().build();
    }

}
