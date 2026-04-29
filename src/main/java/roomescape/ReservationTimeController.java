package roomescape;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReservationTimeController {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @PostMapping("/times")
    public ReservationTime addReservationTime(@RequestBody ReservationTimeSaveDto reservationTimeSaveDto) {
        return reservationTimeRepository.save(
                reservationTimeSaveDto.startAt()
        );
    }

    @GetMapping("/times")
    public List<ReservationTime> getAllReservationTimes() {
        return reservationTimeRepository.findAll();
    }

    @DeleteMapping("/times/{id}")
    public void deleteReservationTime(@PathVariable Long id) {
        reservationTimeRepository.deleteById(id);
    }
}
