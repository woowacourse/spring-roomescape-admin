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
import roomescape.dto.request.ReservationTimeCreateRequest;
import roomescape.dto.response.ReservationTimeResponse;
import roomescape.service.ReservationTimeService;

@Controller
@RequestMapping("/times")
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> notFoundInDB() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("DB에서 데이터를 찾을 수 없습니다.");
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> getTimes() {
        List<ReservationTimeResponse> responses = reservationTimeService.findAll();

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationTimeResponse> getTimeById(@PathVariable long id) {
        ReservationTimeResponse response = reservationTimeService.findById(id);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> createTime(@RequestBody ReservationTimeCreateRequest request) {
        ReservationTimeResponse response = reservationTimeService.save(request);
        URI uri = URI.create("/times/" + response.id());

        return ResponseEntity.created(uri).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimeById(@PathVariable long id) {
        reservationTimeService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
