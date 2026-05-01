package roomescape.reservationtime;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeRepository reservationTimeRepository;


    public ReservationTimeController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationTimeResponseDTO create(@RequestBody ReservationTimeRequestDTO reservationTimeRequestDTO) {
        System.out.println("hihi");
        ReservationTime reservationTime = new ReservationTime(reservationTimeRequestDTO.getStartAt());
        System.out.println("reservationTime = " + reservationTime);
        Long id = reservationTimeRepository.insert(reservationTime);
        return new ReservationTimeResponseDTO(
                id,
                reservationTime.getStartAt());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationTimeResponseDTO> read() {
        return reservationTimeRepository.findAllReservationTimes().stream()
                .map(reservationTime -> new ReservationTimeResponseDTO(
                        reservationTime.getId(),
                        reservationTime.getStartAt()
                )).toList();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable long id) {
        reservationTimeRepository.delete(id);
    }
}
