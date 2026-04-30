package roomescape.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.dao.ReservationDAO;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@Controller
public class ReservationController {

    @Autowired
    private ReservationDAO reservationDAO;

    @ResponseBody
    @PostMapping("/reservations")
    public ReservationResponse create(@RequestBody ReservationRequest request) {
        Reservation reservation = reservationDAO.insert(request.getName(), request.getDate(), request.getTimeId());
        return ReservationResponse.from(reservation);
    }

    @ResponseBody
    @GetMapping("/reservations")
    public List<ReservationResponse> findAll() {
        return reservationDAO.findAll().stream()
                .map(ReservationResponse::from)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationDAO.delete(id);
        return ResponseEntity.ok().build();
    }
}
