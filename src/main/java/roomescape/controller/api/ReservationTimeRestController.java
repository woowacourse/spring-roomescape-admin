package roomescape.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import roomescape.dto.ReservationTimeCreateRequest;
import roomescape.dto.ReservationTimeGetResponse;
import roomescape.model.ReservationTime;
import roomescape.service.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/times")
public class ReservationTimeRestController {

    private final ReservationService reservationService;

    public ReservationTimeRestController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<ReservationTimeGetResponse> getReservationTimes() {
        List<ReservationTime> reservationTimes = reservationService.getAllReservationTime();
        return reservationTimes.stream()
                .map(ReservationTimeGetResponse::from)
                .toList();
    }

    @PostMapping
    public ReservationTimeGetResponse addReservationTime(@RequestBody ReservationTimeCreateRequest reservationTimeCreateRequest) {
        try {
            ReservationTime newReservationTime = reservationService.createNewReservationTime(reservationTimeCreateRequest);
            return ReservationTimeGetResponse.from(newReservationTime);
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteReservationTime(@PathVariable("id") Long id) {
        try {
            reservationService.deleteReservationTimeById(id);
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
