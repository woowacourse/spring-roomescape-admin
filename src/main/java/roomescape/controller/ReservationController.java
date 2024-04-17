package roomescape.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.service.ReservationService;

@RestController
public class ReservationController {

    private final ReservationService reservationService;    // TODO: 이 컨트롤러는 언제 생성될까? 필드 초기화는 어디서 해야할까?

    public ReservationController() {
        this.reservationService = new ReservationService();
    }

    @GetMapping("/reservations")
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationResponse> read() {
        return reservationService.read();
    }

    @PostMapping("/reservations")
    @ResponseStatus(HttpStatus.OK)
    public ReservationResponse add(@RequestBody final ReservationRequest reservationRequest) {
        return reservationService.add(reservationRequest);
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable(name = "id") final Long id) {
        reservationService.remove(id);
    }
}
