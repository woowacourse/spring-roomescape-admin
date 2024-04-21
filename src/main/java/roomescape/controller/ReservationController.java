package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationDto;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationDao reservationDao;

    public ReservationController(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @GetMapping
    public ResponseEntity<List<ReservationDto>> getAll() {
        List<Reservation> reservations = reservationDao.findAll();
        List<ReservationDto> results = reservations.stream()
                .map(ReservationDto::from)
                .toList();
        return ResponseEntity.ok(results);
    }

    @PostMapping
    public ResponseEntity<ReservationDto> create(@RequestBody ReservationDto reservationDto) {
        long id = reservationDao.add(reservationDto);
        Reservation reservation = reservationDao.findById(id);
        return ResponseEntity.created(URI.create("/reservations"))
                .body(ReservationDto.from(reservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationDao.delete(id);
        return ResponseEntity.noContent().build();
    }
}
