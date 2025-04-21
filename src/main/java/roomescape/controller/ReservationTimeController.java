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
import roomescape.dto.CreateReservationTimeDto;
import roomescape.dto.ReservationTimeDto;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.ReservationTimeService;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeController(ReservationTimeService reservationTimeService,
                                     ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeService = reservationTimeService;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @GetMapping
    public List<ReservationTimeDto> getAllReservationTime() {
        return reservationTimeService.findAllReservationTime();
    }

    @PostMapping
    public ResponseEntity<ReservationTimeDto> createReservationTime(
            @RequestBody CreateReservationTimeDto createReservationTimeDto) {
        ReservationTimeDto reservationTimeDto = reservationTimeService.createReservationTime(createReservationTimeDto);
        return ResponseEntity.ok(reservationTimeDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable("id") Long id) {
        reservationTimeService.deleteReservationTime(id);
        return ResponseEntity.ok().build();
    }
}
