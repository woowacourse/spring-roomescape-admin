package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.data.dto.ReservationTimeRequest;
import roomescape.data.vo.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@RestController
@RequestMapping("/time")
public class ReservationTimeController {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @PostMapping
    public ResponseEntity<ReservationTime> createTime(@RequestBody final ReservationTimeRequest dto) {
        long savedId = reservationTimeRepository.add(new ReservationTime(dto.getStartAt()));

        return ResponseEntity.ok(new ReservationTime(savedId, dto.getStartAt()));
    }
}
