package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.ReservationInfo;
import roomescape.dto.ReservationResponse;

@RestController
public class RoomescapeRestController {
    private final List<ReservationInfo> reservationInfos = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/reservations")
    public List<ReservationResponse> reservations() {
        return reservationInfos.stream()
                .map(ReservationResponse::new)
                .toList();
    }

    @PostMapping("/reservations")
    public ReservationResponse addReservationInfo(@RequestBody ReservationInfo reservationInfo) {
        ReservationInfo newReservationInfo = ReservationInfo.toEntity(reservationInfo, counter.incrementAndGet());
        reservationInfos.add(newReservationInfo);
        return new ReservationResponse(newReservationInfo);
    }

    @DeleteMapping("/reservations/{id}")
    public void deleteReservationInfo(@PathVariable Long id) {
        reservationInfos.removeIf(reservationInfo -> reservationInfo.getId().getId().equals(id));
    }
}
