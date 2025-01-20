package roomescape.controller.api;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.Login;
import roomescape.domain.Member;
import roomescape.service.ReservationFacadeService;
import roomescape.service.dto.ReservationMineResponse;
import roomescape.service.dto.ReservationRequest;
import roomescape.service.dto.ReservationResponse;

@RequiredArgsConstructor
@RequestMapping("/reservations")
@RestController
public class ReservationController {

    private final ReservationFacadeService reservationFacadeService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ReservationResponse createReservation(
            @RequestBody ReservationRequest reservationRequest,
            @Login Member member
    ) {
        return reservationFacadeService.createReservation(reservationRequest, member);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/waitings")
    public ReservationResponse createWaiting(
            @RequestBody ReservationRequest reservationRequest,
            @Login Member member
    ) {
        return reservationFacadeService.createWaiting(reservationRequest, member);
    }

    @GetMapping
    public List<ReservationResponse> findAllReservations() {
        return reservationFacadeService.findAll();
    }

    @GetMapping("/mine")
    public List<ReservationMineResponse> findReservationsOfMember(@Login Member member) {
        return reservationFacadeService.findOfMember(member);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void cancelReservation(@PathVariable Long id, @Login Member member) {
        reservationFacadeService.remove(id, member);
    }
}
