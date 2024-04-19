package roomescape.web;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.Reservation;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationMemoryDao;
import roomescape.web.dto.ReservationFindResponse;
import roomescape.web.dto.ReservationSaveRequest;

@RequestMapping("/reservations")
@RestController
public class ReservationController {

    private final ReservationDao reservationDao = new ReservationMemoryDao();
    private final AtomicLong counter = new AtomicLong();

    @GetMapping
    public List<ReservationFindResponse> findAllReservation() {
        List<Reservation> reservations = reservationDao.findAll();
        return reservations.stream()
                .map(ReservationFindResponse::from)
                .toList();
    }

    @PostMapping
    public ReservationFindResponse saveReservation(@RequestBody ReservationSaveRequest request) {
        Reservation reservation = request.toEntity(counter.incrementAndGet());
        reservationDao.save(reservation);
        return ReservationFindResponse.from(reservation);
    }

    @DeleteMapping("/{reservation_id}")
    public void deleteReservation(@PathVariable(value = "reservation_id") Long id) {
        Reservation reservation = findReservationById(id);
        reservationDao.delete(reservation);
    }

    private Reservation findReservationById(Long id) {
        return reservationDao.findById(id)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 reservation ID 입니다."));
    }
}
