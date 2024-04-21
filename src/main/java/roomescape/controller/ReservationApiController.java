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
import roomescape.dto.reservation.ReservationRequestDto;
import roomescape.dto.reservation.ReservationResponseDto;
import roomescape.repository.reservation.JdbcReservationRepository;

@RestController
@RequestMapping("/reservations")
public class ReservationApiController {

    private final JdbcReservationRepository jdbcReservationRepository;

    public ReservationApiController(JdbcReservationRepository jdbcReservationRepository) {
        this.jdbcReservationRepository = jdbcReservationRepository;
    }

    @GetMapping
    public List<ReservationResponseDto> getReservations() {
        return jdbcReservationRepository.findAllReservations()
                .stream()
                .map(ReservationResponseDto::new)
                .toList();
    }

    @PostMapping
    public ReservationResponseDto createReservation(@RequestBody ReservationRequestDto requestDto) {
        Reservation reservation = jdbcReservationRepository.insertReservation(requestDto.toReservation());
        return new ReservationResponseDto(reservation);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable long id) {
        jdbcReservationRepository.deleteReservationById(id);
    }
}
