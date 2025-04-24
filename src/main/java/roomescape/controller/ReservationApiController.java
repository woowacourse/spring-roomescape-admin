package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.repository.ReservationRepository;
import roomescape.service.ReservationService;

@RestController
@RequestMapping("/reservations")
public final class ReservationApiController {

    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;

    public ReservationApiController(ReservationRepository reservationRepository,
                                    final ReservationService reservationService) {
        this.reservationRepository = reservationRepository;
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<ReservationResponseDto> reservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponseDto::from)
                .toList();
    }

    @PostMapping
    public ReservationResponseDto reserve(@RequestBody ReservationRequestDto reservationRequestDto) {
        return reservationService.createReservation(reservationRequestDto);
    }

    @DeleteMapping("/{id}")
    public void cancel(@PathVariable Long id) {
        reservationRepository.removeById(id);
    }
}
