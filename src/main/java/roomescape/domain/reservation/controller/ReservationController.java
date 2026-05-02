package roomescape.domain.reservation.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.reservation.dto.ReservationRequestDTO;
import roomescape.domain.reservation.dto.ReservationResponseDTO;
import roomescape.domain.reservation.service.ReservationService;

@RestController
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/reservations")
    public ReservationResponseDTO create(@RequestBody @Valid ReservationRequestDTO requestDTO) {
        return ReservationResponseDTO.from(reservationService.create(requestDTO));
    }

    @GetMapping("/reservations")
    public List<ReservationResponseDTO> getAll() {
        return reservationService.getAll().stream()
                .map(ReservationResponseDTO::from)
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/reservations/{id}")
    public void delete(@PathVariable Long id) {
        reservationService.delete(id);
    }
}
