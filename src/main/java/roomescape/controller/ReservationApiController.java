package roomescape.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.entity.Reservation;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationApiController {

    private final List<Reservation> reservationEntities;

    public ReservationApiController(List<Reservation> reservationEntities) {
        this.reservationEntities = reservationEntities;
    }

    @GetMapping
    public List<ReservationResponseDto> getReservations() {
        return reservationEntities.stream()
                .map(ReservationResponseDto::new)
                .toList();
    }

    @PostMapping
    public ReservationResponseDto createReservation(
            @RequestBody ReservationRequestDto reservationRequestDto) {

        Reservation reservation = reservationRequestDto.toEntity();
        reservationEntities.add(reservation);
        return new ReservationResponseDto(reservation);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        reservationEntities.removeIf(reservation -> reservation.getId().equals(id));
    }
}
