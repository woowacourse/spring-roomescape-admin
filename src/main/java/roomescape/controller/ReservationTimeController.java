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
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeFindResponse;
import roomescape.dto.ReservationTimeSaveRequest;
import roomescape.service.ReservationTimeService;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    public List<ReservationTimeFindResponse> findAllReservationTime() {
        return reservationTimeService.findAll();
    }

    @PostMapping
    public ResponseEntity<ReservationTimeFindResponse> saveReservationTime(
            @RequestBody ReservationTimeSaveRequest request) {
        ReservationTime reservationTime = request.toEntity();
        ReservationTime saveReservationTime = reservationTimeService.save(reservationTime);
        return ResponseEntity.ok()
                .header("Location", "/reservations/" + saveReservationTime.getId())
                .body(ReservationTimeFindResponse.from(saveReservationTime));
    }

    @DeleteMapping("/{reservation_time_id}")
    public void deleteReservationTime(@PathVariable(value = "reservation_time_id") Long id) {
        reservationTimeService.deleteById(id);
    }

}
