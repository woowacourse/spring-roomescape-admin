package roomescape.reservation.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.reservation.dto.ReservationTimeRequestDto;
import roomescape.reservation.dto.ReservationTimeResponseDto;
import roomescape.reservation.service.ReservationTimeService;

@Controller
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping("/admin/time")
    public String adminReservationTimeDashboard() {
        return "admin/time";
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTimeResponseDto>> readAllReservationTimes() {

        List<ReservationTimeResponseDto> responseDtos = reservationTimeService.getAll();

        return ResponseEntity.ok(responseDtos);
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTimeResponseDto> add(@RequestBody ReservationTimeRequestDto requestDto) {

        ReservationTimeResponseDto responseDto = reservationTimeService.save(requestDto);

        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        reservationTimeService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
