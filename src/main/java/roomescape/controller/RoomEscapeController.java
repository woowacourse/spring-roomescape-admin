package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.dto.ReservationRequestDto;
import roomescape.service.RoomEscapeService;
import roomescape.domain.Reservation;

@Controller
public class RoomEscapeController {

    private List<Reservation> reservations = new ArrayList<>();
    private final RoomEscapeService roomEscapeService;

    public RoomEscapeController(RoomEscapeService roomEscapeService) {
        this.roomEscapeService = roomEscapeService;
    }

    @GetMapping("admin")
    public String home() {
        return "admin/index";
    }

    @GetMapping("admin/reservation")
    public String reservation() {
        return "admin/reservation-legacy";
    }

    @GetMapping("reservations")
    @ResponseBody
    public List<Reservation> readReservations() {
        return reservations;
    }

    @PostMapping("reservations")
    @ResponseBody
    public Reservation add(@RequestBody ReservationRequestDto reservationDto) {
        Reservation reservation = roomEscapeService.convertReservation(reservationDto);
        reservations.add(reservation);
        return reservation;
    }

    @DeleteMapping("reservations/{reservationId}")
    @ResponseBody
    public void delete(@PathVariable("reservationId") Long id) {
        Reservation reservation = reservations.stream()
                .filter(v -> v.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 예약 번호입니다:" + id));
        reservations.remove(reservation);
    }
}
