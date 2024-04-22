package roomescape.admin.reservation.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.admin.reservation.dto.request.ReservationTimeRequest;
import roomescape.admin.reservation.dto.response.ReservationTimeResponse;
import roomescape.admin.reservation.entity.ReservationTime;
import roomescape.admin.reservation.repository.ReservationTimeRepository;
import roomescape.admin.reservation.service.ReservationTimeService;

@RequestMapping("/times")
@RestController
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;
    private final ReservationTimeRepository reservationTimeRepository;

    @Autowired
    public ReservationTimeController(ReservationTimeService reservationTimeService,
                                     ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeService = reservationTimeService;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> findAll() {
        List<ReservationTimeResponse> reservationTimeResponse = reservationTimeService.findAll().stream()
                .map(ReservationTimeResponse::from)
                .toList();

        return ResponseEntity.ok(reservationTimeResponse);
    }

    @PostMapping
    public ResponseEntity<ReservationTime> create(@RequestBody ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = reservationTimeService.create(reservationTimeRequest);
        return ResponseEntity.ok(reservationTime);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        int deleteCount = reservationTimeService.delete(id);
        if (deleteCount == 0) {
            return ResponseEntity.ok("삭제할 시간이 존재하지 않습니다.");
        }
        return ResponseEntity.ok().build();
    }
}
