package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.service.ReservationService;

@Controller
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> notFoundInDB() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("DB에서 데이터를 찾을 수 없습니다.");
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        List<ReservationResponse> responses = reservationService.findAll();

        return ResponseEntity.ok(responses);
    }

    @GetMapping("{id}")
    public ResponseEntity<ReservationResponse> getReservation(@PathVariable long id) {
        ReservationResponse response = reservationService.findById(id);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationCreateRequest request) {
        ReservationResponse response = reservationService.save(request);
        URI uri = URI.create("/reservations/" + response.id());

        return ResponseEntity.created(uri).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationById(@PathVariable long id) {
        reservationService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
