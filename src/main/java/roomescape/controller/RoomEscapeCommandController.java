package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.domain.dto.ReservationRequestDto;
import roomescape.service.RoomEscapeService;

@RestController
public class RoomEscapeCommandController {

    private final RoomEscapeService roomEscapeService;
    private List<Reservation> reservations = new ArrayList<>();

    public RoomEscapeCommandController(RoomEscapeService roomEscapeService) {
        this.roomEscapeService = roomEscapeService;
    }

    @GetMapping("reservations")
    public List<Reservation> readReservations() {
        return reservations;
    }

    @PostMapping("reservations")
    public Reservation add(@RequestBody ReservationRequestDto reservationDto) {
        Reservation reservation = roomEscapeService.convertReservation(reservationDto);
        reservations.add(reservation);
        return reservation;
    }

    @DeleteMapping("reservations/{reservationId}")
    public void delete(@PathVariable("reservationId") Long id) {
        Reservation reservation = reservations.stream()
                .filter(v -> v.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 예약 번호입니다:" + id));
        reservations.remove(reservation);
    }
}
