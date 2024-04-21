package roomescape.admin.reservation;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/reservations")
@RestController
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();
    private final AtomicLong atomicLong = new AtomicLong(1);

    @Autowired
    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping
    public ResponseEntity<List<ResponseReservation>> findAll() {
        List<Reservation> reservations = reservationRepository.findAll();
        List<ResponseReservation> responseReservations = reservations.stream()
                .map(ResponseReservation::from)
                .toList();

        return ResponseEntity.ok(responseReservations);
    }

    @PostMapping
    public ResponseEntity<ResponseReservation> create(@RequestBody RequestReservation requestReservation) {
        Reservation reservation = new Reservation(atomicLong.getAndIncrement(), requestReservation.name(),
                requestReservation.date(), requestReservation.time());
        reservations.put(reservation.getId(), reservation);

        return ResponseEntity.ok(ResponseReservation.from(reservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Reservation reservation = reservations.get(id);
        if (reservation == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("삭제할 예약이 존재하지 않습니다.");
        }
        reservations.remove(id);

        return ResponseEntity.ok().build();
    }
}
