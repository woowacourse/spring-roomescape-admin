package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.dto.ReservationCreateRequestDto;
import roomescape.controller.dto.ReservationResponseDto;
import roomescape.domain.Reservation;
import roomescape.service.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<ReservationResponseDto> findAll() {
        List<Reservation> reservations = reservationService.findAll();

        return reservations.stream()
                .map(ReservationResponseDto::toDto)
                .toList();
    }

    @PostMapping
    public ReservationResponseDto create(@RequestBody ReservationCreateRequestDto dto) {
        Reservation reservation = reservationService.save(dto);

        return ReservationResponseDto.toDto(reservation);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        reservationService.deleteById(id);
    }
}
