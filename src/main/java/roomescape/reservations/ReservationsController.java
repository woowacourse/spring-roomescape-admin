package roomescape.reservations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRepositoryDto;
import roomescape.dto.ReservationTimeDto;
import roomescape.dto.request.ReservationsRequest;
import roomescape.dto.response.ReservationsResponse;
import roomescape.times.ReservationTimeRepository;

import java.util.List;

@Controller
@RequestMapping("/reservations")
public class ReservationsController {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    @GetMapping
    @ResponseBody
    public List<ReservationsResponse> reservations() {
        return reservationRepository.findAll()
                .stream()
                .map(this::reservationRepositoryDtoToReservation)
                .map(ReservationsResponse::new)
                .toList();
    }

    @PostMapping
    @ResponseBody
    public ReservationsResponse addReservationInfo(@RequestBody ReservationsRequest reservationsRequest) {
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
    public void deleteReservationInfo(@PathVariable Long id) {
        reservationRepository.remove(id);
    }
}
