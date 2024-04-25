package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRepositoryDto;
import roomescape.dto.ReservationTimeDto;
import roomescape.dto.request.ReservationsRequest;
import roomescape.dto.response.ReservationsResponse;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationsController {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationsController(ReservationRepository reservationRepository, ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @GetMapping
    public List<ReservationsResponse> reservations() {
        return reservationRepository.findAll()
                .stream()
                .map(this::reservationRepositoryDtoToReservation)
                .map(ReservationsResponse::new)
                .toList();
    }

    @PostMapping
    public ReservationsResponse addReservation(@RequestBody ReservationsRequest reservationsRequest) {
        ReservationRepositoryDto requestReservationDto = new ReservationRepositoryDto(null, reservationsRequest.name(), reservationsRequest.date(), reservationsRequest.timeId());
        ReservationRepositoryDto newReservationDto = reservationRepository.add(requestReservationDto);
        Reservation newReservation = reservationRepositoryDtoToReservation(newReservationDto);
        return new ReservationsResponse(newReservation);
    }

    private Reservation reservationRepositoryDtoToReservation(ReservationRepositoryDto reservationRepositoryDto) {
        ReservationTimeDto reservationTimeDto = reservationTimeRepository.findById(reservationRepositoryDto.timeId());
        return new Reservation(reservationRepositoryDto.id(), reservationRepositoryDto.name(), reservationRepositoryDto.date(), reservationTimeDto.toDomain());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public int deleteReservation(@PathVariable Long id) {
        return reservationRepository.remove(id);
    }
}
