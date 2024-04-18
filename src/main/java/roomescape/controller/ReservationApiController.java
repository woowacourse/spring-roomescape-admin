package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import roomescape.dao.ReservationDao;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.entity.Reservation;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationApiController {

    private final ReservationDao reservationDao;

    public ReservationApiController(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @GetMapping
    public List<ReservationResponseDto> getReservations() {
        List<Reservation> reservations = reservationDao.findReservations();
        return reservations.stream()
                .map(ReservationResponseDto::from)
                .toList();
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> postReservation(@RequestBody ReservationRequestDto reservationRequestDto) {
        Reservation reservation = reservationDao.createReservation(reservationRequestDto.toEntity());
        URI location = UriComponentsBuilder.newInstance()
                .path("/reservations/{id}")
                .buildAndExpand(reservation.getId())
                .toUri();

        return ResponseEntity.created(location).body(ReservationResponseDto.from(reservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationDao.removeReservation(id);
        return ResponseEntity.noContent().build();
    }
}
