package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.repository.JdbcTemplateReservationRepository;

@RestController
@RequestMapping("/reservations")
public class ReservationApiController {

    private final List<Reservation> reservationEntities;
    private final JdbcTemplateReservationRepository jdbcTemplateReservationRepository;

    public ReservationApiController(List<Reservation> reservationEntities,
                                    JdbcTemplateReservationRepository jdbcTemplateReservationRepository) {
        this.reservationEntities = reservationEntities;
        this.jdbcTemplateReservationRepository = jdbcTemplateReservationRepository;
    }

    @GetMapping
    public List<ReservationResponseDto> getReservations() {
        return jdbcTemplateReservationRepository.findAllReservations()
                .stream()
                .map(ReservationResponseDto::new)
                .toList();
    }

    @PostMapping
    public ReservationResponseDto createReservation(@RequestBody ReservationRequestDto reservationRequestDto) {
        Reservation reservation = reservationRequestDto.toEntity();
        reservationEntities.add(reservation);
        return new ReservationResponseDto(reservation);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        reservationEntities.removeIf(reservation -> reservation.getId().equals(id));
    }
}
