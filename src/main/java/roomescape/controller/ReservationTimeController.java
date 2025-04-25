package roomescape.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.controller.dto.ReservationTimeRegister;
import roomescape.controller.dto.ReservationTimeResponse;
import roomescape.service.ReservationService;

@Controller
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationTimeController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String time() {
        return "/admin/time";
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReservationTimeResponse>> getAllTimes() {
        return ResponseEntity.ok(reservationService.getAllReservationTime());
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> save(
            @RequestBody final ReservationTimeRegister reservationTimeRegister
    ) {
        return ResponseEntity.ok(reservationService.saveReservationTime(reservationTimeRegister));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(final @PathVariable(name = "id") Long id) {
        try {
            reservationService.deleteReservationTimeById(id);
            return ResponseEntity.ok().build();
        } catch (final IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
