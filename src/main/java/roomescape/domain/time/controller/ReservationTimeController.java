package roomescape.domain.time.controller;

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
import roomescape.domain.time.dto.ReservationTimeRequestDTO;
import roomescape.domain.time.dto.ReservationTimeResponseDTO;
import roomescape.domain.time.service.ReservationTimeService;

@RestController
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/times")
    public ReservationTimeResponseDTO create(@RequestBody ReservationTimeRequestDTO requestDTO) {
        return ReservationTimeResponseDTO.from(reservationTimeService.create(requestDTO));
    }

    @GetMapping("/times")
    public List<ReservationTimeResponseDTO> getAll() {
        return reservationTimeService.getAll().stream()
                .map(ReservationTimeResponseDTO::from)
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/times/{id}")
    public void delete(@PathVariable Long id) {
        reservationTimeService.delete(id);
    }
}
