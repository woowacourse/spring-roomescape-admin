package roomescape.controller;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.ReservationTime;
import roomescape.dto.response.ReservationTimeCreateResponse;
import roomescape.dto.response.ReservationTimeFindAllResponse;
import roomescape.service.ReservationFacade;

@Controller
public class ReservationTimeController {

    private final ReservationFacade reservationFacade;

    public ReservationTimeController(ReservationFacade reservationFacade) {
        this.reservationFacade = reservationFacade;
    }

    @ResponseBody
    @PostMapping("/times")
    public ResponseEntity<ReservationTimeCreateResponse> create(@RequestBody ReservationTime reservationTime) {
        ReservationTimeCreateResponse saved = reservationFacade.createReservationTime(reservationTime);
        return ResponseEntity.ok(saved);
    }

    @ResponseBody
    @GetMapping("/times")
    public ResponseEntity<List<ReservationTimeFindAllResponse>> findAll() {
        return ResponseEntity.ok(reservationFacade.findAllReservationTime());
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            reservationFacade.deleteReservationTime(id);
            return ResponseEntity.ok().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
