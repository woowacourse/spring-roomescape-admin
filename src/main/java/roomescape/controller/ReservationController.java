package roomescape.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import roomescape.domain.Reservation;
import roomescape.domain.User;
import roomescape.domain.repository.ReservationRepository;
import roomescape.dto.ReservationRequestDTO;
import roomescape.dto.ReservationResponseDTO;

@Controller
public class ReservationController {
    private final ReservationRepository repository;

    public ReservationController(ReservationRepository repository) {
        this.repository = repository;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/reservations")
    public ReservationResponseDTO create(@RequestBody ReservationRequestDTO requestDTO) {
        Reservation reservation = new Reservation(null, new User(null, requestDTO.getName()), requestDTO.getDate(), requestDTO.getTime());
        Long id = repository.save(reservation);
        return ReservationResponseDTO.from(
                new Reservation(id, reservation.getUser(), reservation.getDate(), reservation.getTime()));
    }

    @ResponseBody
    @GetMapping("/reservations")
    public List<ReservationResponseDTO> read() {
        return repository.findAll().stream()
                .map(ReservationResponseDTO::from)
                .collect(Collectors.toList());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/reservations/{id}")
    public void delete(@PathVariable Long id) {
        repository.delete(id);
    }
}