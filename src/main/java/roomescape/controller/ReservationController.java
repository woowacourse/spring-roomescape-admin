package roomescape.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import roomescape.dao.QueryingDAO;
import roomescape.dao.UpdatingDAO;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationCreateRequest;
import roomescape.dto.ReservationCreateResponse;
import roomescape.dto.TimeCreateResponse;
import roomescape.service.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<Reservation> read() {
        QueryingDAO dao = reservationService.getQueryingDAO();
        return dao.findAllReservations();
    }

    @PostMapping
    public ReservationCreateResponse create(
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
        return response;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        UpdatingDAO dao = reservationService.getUpdatingDAO();
        dao.delete(Long.valueOf(id));
    }
}
