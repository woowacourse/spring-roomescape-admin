package roomescape.controller.api;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.reservationtime.ReservationTimeRequest;
import roomescape.dto.reservationtime.ReservationTimeResponse;

@RequestMapping("/times")
@Controller
public class TimeController {

    private final ReservationTimeDao reservationTimeDao;
    private final ReservationDao reservationDao;

    public TimeController(ReservationTimeDao reservationTimeDao, ReservationDao reservationDao) {
        this.reservationTimeDao = reservationTimeDao;
        this.reservationDao = reservationDao;
    }

    @GetMapping("")
    public ResponseEntity<List<ReservationTimeResponse>> times() {
        List<ReservationTimeResponse> reservationTimeResponses = reservationTimeDao.findAllReservationTimes()
                .stream()
                .map(ReservationTimeResponse::from)
                .toList();

        return ResponseEntity.ok(reservationTimeResponses);
    }

    @PostMapping("")
    public ResponseEntity<ReservationTimeResponse> create(
            @RequestBody @Valid ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = reservationTimeRequest.toReservationTime();

        long id = reservationTimeDao.saveReservationTime(reservationTime);
        ReservationTimeResponse reservationTimeResponse = new ReservationTimeResponse(
                id,
                reservationTimeRequest.startAt().toString());

        return ResponseEntity.ok(reservationTimeResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (reservationDao.hasReservationOf(id)) {
            throw new IllegalStateException("해당 시간에 예약이 있어 삭제할 수 없습니다.");
        }
        reservationTimeDao.deleteReservationTime(id);

        return ResponseEntity.ok().build();
    }
}
