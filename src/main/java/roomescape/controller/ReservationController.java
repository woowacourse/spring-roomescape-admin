package roomescape.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import roomescape.dao.QueryingDAO;
import roomescape.dao.UpdatingDAO;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationCreateRequest;
import roomescape.dto.ReservationCreateResponse;
import roomescape.dto.TimeCreateRequest;
import roomescape.dto.TimeCreateResponse;
import roomescape.service.ReservationService;

@Controller
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
        QueryingDAO dao = reservationService.getQueryingDAO();
        return ResponseEntity.ok(dao.findAllReservations());
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationCreateResponse> create(
            @RequestBody ReservationCreateRequest request
    ) {
        UpdatingDAO dao = reservationService.getUpdatingDAO();
        QueryingDAO queryingDAO = reservationService.getQueryingDAO();
        ReservationTime reservationTime = queryingDAO.findTimeById(request.timeId());
        Reservation newReservation = new Reservation(request.name(), request.date(), reservationTime);

        Long id = dao.insertWithKeyHolder(newReservation);
        TimeCreateResponse timeResponse = new TimeCreateResponse(reservationTime.getId(), reservationTime.getStartAt());
        ReservationCreateResponse response = new ReservationCreateResponse(
                id,
                newReservation.getName(),
                newReservation.getDate(),
                timeResponse);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        UpdatingDAO dao = reservationService.getUpdatingDAO();
        dao.delete(Long.valueOf(id));
        return ResponseEntity.ok().build();
    }
}
