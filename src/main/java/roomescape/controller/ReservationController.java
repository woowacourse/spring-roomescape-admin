package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.model.Reservation;
import roomescape.repository.ReservationRepository;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationRepository repository;

    public ReservationController(ReservationRepository repository) {
        this.repository = repository;
    }

    @ResponseBody
    @GetMapping
    public List<ReservationResponse> getAllReservations() {
        List<Reservation> allReservations = repository.findAll();
        return ReservationResponse.reservationsToDtos(allReservations);
    }

    @ResponseBody
    @PostMapping
    public ReservationResponse addReservation(@RequestBody ReservationRequest request) {
        Long id = repository.add(request.dtoToReservationWithoutId());
        return ReservationResponse.reservationToDto(repository.findById(id));
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable("id") Long id) {
        repository.deleteById(id);
    }
}
