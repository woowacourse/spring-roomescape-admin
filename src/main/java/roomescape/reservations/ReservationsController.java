package roomescape.reservations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.RequestReservation;
import roomescape.dto.ReservationRepositoryDto;
import roomescape.dto.ResponseReservation;
import roomescape.dto.ReservationTimeDto;
import roomescape.times.ReservationTimeRepository;

import java.net.URI;
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
    public List<ResponseReservation> reservations() {
        return reservationRepository.findAll()
                .stream()
                .map(this::reservationRepositoryDtoToReservation)
                .map(ResponseReservation::new)
                .toList();
    }

    @PostMapping
    public ResponseEntity<ResponseReservation> addReservationInfo(@RequestBody RequestReservation requestReservation) {
        ReservationRepositoryDto requestReservationDto = new ReservationRepositoryDto(null, requestReservation.name(), requestReservation.date(), requestReservation.timeId());
        ReservationRepositoryDto newReservationDto = reservationRepository.add(requestReservationDto);
        Reservation newReservation = reservationRepositoryDtoToReservation(newReservationDto);
        return ResponseEntity.created(URI.create("/reservations/" + newReservationDto.id()))
                .body(new ResponseReservation(newReservation));
    }

    private Reservation reservationRepositoryDtoToReservation(ReservationRepositoryDto reservationRepositoryDto) {
        ReservationTimeDto reservationTimeDto = reservationTimeRepository.findById(reservationRepositoryDto.timeId());
        return new Reservation(reservationRepositoryDto.id(), reservationRepositoryDto.name(), reservationRepositoryDto.date(), reservationTimeDto.toDomain());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservationInfo(@PathVariable Long id) {
        reservationRepository.remove(id);
    }
}
