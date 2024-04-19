package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.repository.JdbcTemplateReservationRepository;

@RestController
@RequestMapping("/reservations")
public class ReservationApiController {

    private final JdbcTemplateReservationRepository jdbcTemplateReservationRepository;

    public ReservationApiController(JdbcTemplateReservationRepository jdbcTemplateReservationRepository) {
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
    public ResponseEntity<ReservationResponseDto> createReservation(@RequestBody ReservationRequestDto requestDto) {
        Reservation reservation = requestDto.toReservation();
        Long id = jdbcTemplateReservationRepository.insertReservation(reservation);

        ReservationResponseDto responseDto =
                new ReservationResponseDto(id, reservation.getName(), reservation.getDate(), reservation.getTime());

        URI uri = URI.create("/reservations/" + id);
        return ResponseEntity.created(uri).body(responseDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable long id) {
        jdbcTemplateReservationRepository.deleteReservationById(id);
    }
}
