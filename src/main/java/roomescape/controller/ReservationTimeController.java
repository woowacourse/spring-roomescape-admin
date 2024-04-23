package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.ReservationTimeDao;
import roomescape.dto.ReservationTimeCreateRequest;
import roomescape.dto.ReservationTimeResponse;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeController(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> findAllReservationTimes() {
        List<ReservationTimeResponse> reservationTimeResponses = reservationTimeDao.findAll()
                .stream()
                .map(ReservationTimeResponse::toResponse)
                .toList();

        return ResponseEntity.ok()
                .body(reservationTimeResponses);
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> createReservationTime(
            @RequestBody ReservationTimeCreateRequest reservationTimeCreateRequest) {
        Long id = reservationTimeDao.addReservationTime(reservationTimeCreateRequest);
        return ResponseEntity.created(URI.create("/times/" + id))
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        int deletedRowCount = reservationTimeDao.deleteById(id);
        if (deletedRowCount == 0) {
            return ResponseEntity.notFound()
                    .build();
        }
        return ResponseEntity.noContent()
                .build();
    }
}
