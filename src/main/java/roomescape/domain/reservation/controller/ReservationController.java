package roomescape.domain.reservation.controller;

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
import roomescape.domain.reservation.dto.ReservationRequestDTO;
import roomescape.domain.reservation.dto.ReservationResponseDTO;
import roomescape.domain.reservation.service.ReservationService;

@Controller
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/reservations")
    public ReservationResponseDTO create(@RequestBody ReservationRequestDTO requestDTO) {
        return ReservationResponseDTO.from(reservationService.create(requestDTO));
    }

    @ResponseBody
    @GetMapping("/reservations")
    public List<ReservationResponseDTO> read() {
        return reservationService.read().stream()
                .map(ReservationResponseDTO::from)
                .collect(Collectors.toList());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/reservations/{id}")
    public void delete(@PathVariable Long id) {
        reservationService.delete(id);
    }
}