package roomescape.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.entity.Reservation;
import roomescape.repository.ReservationRepository;

@RestController
public class ReservationRestController {

    private final ReservationRepository repository;

    @Autowired
    public ReservationRestController(ReservationRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> readReservation() {
        return repository.findAll();
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ReservationResponseDto postReservation(@RequestBody ReservationRequestDto requestDto) {
        Reservation newReservation = repository.save(requestDto.toEntity(null));
        return new ReservationResponseDto(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseBody
    public void deleteReservation(@PathVariable Long id) {
        repository.delete(id);
    }
}
