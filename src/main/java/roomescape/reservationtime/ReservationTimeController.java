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
    public ReservationTimeResponseDTO create(@RequestBody ReservationTimeRequestDTO reservationTimeRequestDTO) {
        ReservationTime reservationTime = new ReservationTime(null, reservationTimeRequestDTO.getStartAt());
        Long id = reservationTimeRepository.insert(reservationTime);
        return new ReservationTimeResponseDTO(
                id,
                reservationTime.getStartAt()
        );
    }

    @GetMapping
    public List<ReservationTimeResponseDTO> read() {
        return reservationTimeRepository.findAllReservationTimes().stream()
                .map(reservationTime -> new ReservationTimeResponseDTO(
                        reservationTime.getId(),
                        reservationTime.getStartAt()
                )).toList();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        reservationTimeRepository.delete(id);
    }
}
