package roomescape.controller;

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
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.model.Reservation;

@RequestMapping("/reservations")
@RestController
public class ReservationController {

    private final ReservationDao reservationDao;

    public ReservationController(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }//생성자 책임을 분리한다. 싱글톤 객체 주입을 통해서.

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> reservations() {

        List<Reservation> reservations = reservationDao.selectAllReservation();

        List<ReservationResponse> reservationResponses = reservations.stream()
                .map(ReservationResponse::toDto).toList();

        return ResponseEntity.ok().body(reservationResponses);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody ReservationRequest request) {

        Reservation reservation = request.toEntity();

        Reservation addedReservation = reservationDao.addReservation(reservation);

        ReservationResponse reservationResponse = ReservationResponse.toDto(addedReservation);

        return ResponseEntity.ok().body(reservationResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {

        int effectedRow = reservationDao.deleteReservationById(id);

        if (effectedRow == 1) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.noContent().build();
    }
}
