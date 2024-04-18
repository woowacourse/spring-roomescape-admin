package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final List<Reservation> reservations;// TODO: 이 컨트롤러는 언제 생성될까? 필드 초기화는 어디서 해야할까?
    private final AtomicLong index;

    public ReservationController() {
        this.reservations = new ArrayList<>();
        this.index = new AtomicLong(1);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationResponse> findAll() {
        return ReservationResponse.fromReservations(reservations);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ReservationResponse add(@RequestBody final ReservationRequest reservationRequest) {
        long id = index.getAndIncrement();
        Reservation reservation = reservationRequest.toReservation(id);
        reservations.add(reservation);
        return ReservationResponse.from(reservation);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable(name = "id") final long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> it.getId() == id)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        reservations.remove(reservation);
    }
}
