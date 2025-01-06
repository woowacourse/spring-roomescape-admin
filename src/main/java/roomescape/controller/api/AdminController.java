package roomescape.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.Login;
import roomescape.domain.Member;
import roomescape.service.ReservationService;
import roomescape.service.dto.ReservationAdminRequest;
import roomescape.service.dto.ReservationResponse;

@RequiredArgsConstructor
@RequestMapping("/admin")
@RestController
public class AdminController {

    private final ReservationService reservationService;

    @PostMapping("/reservations")
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse createReservation(
            @RequestBody ReservationAdminRequest adminRequest,
            @Login Member admin
    ) {
        return reservationService.create(adminRequest, admin);
    }
}
