package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.controller.dto.ReservationRequest;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.controller.dto.ReservationResponse;
import roomescape.domain.ReservationCreationRequest;
import roomescape.service.ReservationService;

@Controller
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;
    private final ReservationDao reservationDao;

    public ReservationController(ReservationService reservationService, ReservationDao reservationDao) {
        this.reservationService = reservationService;
        this.reservationDao = reservationDao;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> findAll() {
        List<Reservation> reservations = reservationDao.findAll();
        List<ReservationResponse> reservationDtos = reservations.stream()
                .map(ReservationResponse::from)
                .toList();

        return ResponseEntity.ok()
                .body(reservationDtos);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> add(@RequestBody ReservationRequest reservationRequest) {
        ReservationCreationRequest creationRequest = reservationRequest.toCreationRequest();

        Reservation newReservation = reservationService.createReservation(creationRequest);

        return ResponseEntity.ok()
                .body(ReservationResponse.from(newReservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!reservationDao.isExist(id)) {
            return ResponseEntity.noContent().build();
        }
        reservationDao.delete(id);
        return ResponseEntity.ok().build();
    }
}
