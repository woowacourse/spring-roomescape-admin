package roomescape.reservationtime;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @PostMapping
    public ReservationTime create(@RequestBody ReservationTime reservationTime) {
        Long id = reservationTimeRepository.insert(reservationTime);
        return ReservationTime.toEntity(reservationTime, id);
    }

    @GetMapping
    public List<ReservationTime> read() {
        return reservationTimeRepository.findAllReservationTimes();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        reservationTimeRepository.delete(id);
    }
}
