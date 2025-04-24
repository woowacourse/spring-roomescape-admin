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
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.model.ReservationTime;
import roomescape.service.ReservationTimeService;

@RestController
@RequestMapping("/time")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> timeReservations() {
        List<ReservationTime> reservationTimes = reservationTimeService.getTimeReservations();

        List<ReservationTimeResponse> responses = reservationTimes.stream()
                .map(ReservationTimeResponse::toDto)
                .toList();
        return ResponseEntity.ok().body(responses);
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> addReservation(@RequestBody ReservationTimeRequest request) {
        ReservationTime reservationTime = request.toEntity();

        ReservationTime addedReservationTime = reservationTimeService.addReservation(reservationTime);

        ReservationTimeResponse response = ReservationTimeResponse.toDto(addedReservationTime);

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimeReservations(@PathVariable Long id){
        int effectedRow = reservationTimeService.deleteTimeReservationById(id);

        if(effectedRow ==1 ){
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.noContent().build();
    }
}
