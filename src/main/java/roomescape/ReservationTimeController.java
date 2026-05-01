package roomescape;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @PostMapping
    public ReservationTime addReservationTime(@RequestBody ReservationTimeSaveDto reservationTimeSaveDto) {
        return reservationTimeRepository.save(reservationTimeSaveDto.startAt());
    }

    @GetMapping
    public List<ReservationTime> getAllReservationTimes() {
        return reservationTimeRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteReservationTime(@PathVariable Long id) {
        reservationTimeRepository.deleteById(id);
    }
}
