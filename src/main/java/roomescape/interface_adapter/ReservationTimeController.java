package roomescape.interface_adapter;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.usecase.AddReservationTimeUseCase;
import roomescape.usecase.GetReservationTimeUseCase;
import roomescape.usecase.ReservationTimeOutput;

@Controller
public class ReservationTimeController {


    private final AddReservationTimeUseCase addReservationTimeUseCase;
    private final GetReservationTimeUseCase getReservationTimeUseCase;

    public ReservationTimeController(final AddReservationTimeUseCase addReservationTimeUseCase,
                                     final GetReservationTimeUseCase getReservationTimeUseCase) {
        this.addReservationTimeUseCase = addReservationTimeUseCase;
        this.getReservationTimeUseCase = getReservationTimeUseCase;
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
        List<ReservationTimeOutput> reservationTimeOutputs = getReservationTimeUseCase.getReservationTime();
        List<ReservationTimeResponseDto> reservationTimeResponseDtos = reservationTimeOutputs.stream()
                .map(ReservationTimeResponseDto::from)
                .toList();
        return ResponseEntity.ok(reservationTimeResponseDtos);
    }
//
//    @DeleteMapping("/times/{id}")
//    public ResponseEntity<Void> deleteReservationTime(@PathVariable long id) {
//        String sql = "delete from reservation_time where id = ?";
//        jdbcTemplate.update(sql, id);
//        return ResponseEntity.ok().build();
//    }

}
