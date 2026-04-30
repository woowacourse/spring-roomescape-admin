package roomescape.control;

import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.control.dto.CreateReservationTimeRequest;
import roomescape.control.dto.ReservationTimeResponse;
import roomescape.service.ReservationTimeService;

@RequestMapping("/times")
@RestController
@RequiredArgsConstructor
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    /*
    시간 추가	POST /times	{startAt}	{id, startAt}
    시간 조회	GET /times	—	[{id, startAt}, ...]
    시간 삭제	DELETE /times/{id}	—	200 OK
     */

    @PostMapping
    public ReservationTimeResponse createTime(@RequestBody CreateReservationTimeRequest request) {
        final long id = reservationTimeService.createTime(LocalTime.parse(request.startAt()));

        return new ReservationTimeResponse(id, request.startAt());
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> getTimes() {
        final List<ReservationTimeResponse> times = reservationTimeService.getAllTimes().stream()
                .map(time -> new ReservationTimeResponse(time.getId(), time.getReservationTime().toString()))
                .toList();

        return ResponseEntity.ok(times);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable long id) {
        reservationTimeService.deleteTime(id);
        return ResponseEntity.ok().build();
    }
}
