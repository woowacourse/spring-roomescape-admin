package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.dto.ReservationTimeDto;
import roomescape.entity.Reservation;
import roomescape.dao.ReservationDao;

@Controller
public class ReservationController {

    @Autowired
    private ReservationDao reservationDao;

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponseDto>> reservations() {
        List<Reservation> reservations = reservationDao.findAllReservations();
        List<ReservationResponseDto> responseBody = reservations.stream()
                .map(ReservationResponseDto::from)
                .toList();
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponseDto> addReservation(@RequestBody ReservationRequestDto reservationDto) {
        long id = reservationDao.save(reservationDto);
        Reservation reservation = reservationDao.findById(id); // TODO: save + findById ???
        ReservationResponseDto responseBody = new ReservationResponseDto(
                id,
                reservation.getName(),
                reservation.getDate(),
                ReservationTimeDto.from(reservation.getTime())
        );
        return ResponseEntity
                .created(URI.create("/reservations/" + id))
                .body(responseBody);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") long id) {
        reservationDao.delete(id);
        return ResponseEntity.noContent().build();
    }
}
