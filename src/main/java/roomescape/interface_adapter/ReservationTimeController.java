package roomescape.interface_adapter;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.usecase.AddReservationTimeUseCase;
import roomescape.usecase.ReservationTimeOutputModel;

@Controller
public class ReservationTimeController {


    private final AddReservationTimeUseCase addReservationTimeUseCase;

    public ReservationTimeController(final AddReservationTimeUseCase addReservationTimeUseCase) {
        this.addReservationTimeUseCase = addReservationTimeUseCase;
    }


    @PostMapping("/times")
    public ResponseEntity<ReservationTimeResponseDto> addReservationTime(
            @RequestBody ReservationTimeRequestDto reservationTimeRequestDto) {

        ReservationTimeOutputModel reservationTimeOutputModel = addReservationTimeUseCase.addReservationTime(
                reservationTimeRequestDto.startAt());
        ReservationTimeResponseDto reservationTimeResponseDto = ReservationTimeResponseDto.from(
                reservationTimeOutputModel);
        return ResponseEntity.ok(reservationTimeResponseDto);

    }

//    @GetMapping("/times")
//    public ResponseEntity<List<ReservationTimeResponseDto>> getReservationTime() {
//        String sql = "select * from reservation_time";
//        List<ReservationTimeResponseDto> reservationTimeResponseDtos = jdbcTemplate.query(sql, (resultSet, rowNUm) -> {
//            return new ReservationTimeResponseDto(resultSet.getLong("id"),
//                    LocalTime.parse(resultSet.getString("start_at")));
//        });
//        return ResponseEntity.ok(reservationTimeResponseDtos);
//    }
//
//    @DeleteMapping("/times/{id}")
//    public ResponseEntity<Void> deleteReservationTime(@PathVariable long id) {
//        String sql = "delete from reservation_time where id = ?";
//        jdbcTemplate.update(sql, id);
//        return ResponseEntity.ok().build();
//    }

}
