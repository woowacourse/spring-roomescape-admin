package roomescape.reservation;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservationtime.ReservationTime;
import roomescape.reservationtime.ReservationTimeRepository;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationController(ReservationRepository reservationRepository,
                                 ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponseDTO create(@RequestBody ReservationRequestDTO reservationRequestDTO) {
        ReservationTime reservationTime = reservationTimeRepository.findById(reservationRequestDTO.getTimeId());
        Reservation reservation = new Reservation(
                reservationRequestDTO.getName(),
                reservationRequestDTO.getDate(),
                reservationTime);
        Long id = reservationRepository.insert(reservation);
        return new ReservationResponseDTO(
                id,
                reservation.getName(),
                reservation.getDate(),
                reservation.getReservationTime()
        );
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationResponseDTO> read() {
        return reservationRepository.findAllReservations().stream()
                .map(reservation -> new ReservationResponseDTO(
                        reservation.getId(),
                        reservation.getName(),
                        reservation.getDate(),
                        reservation.getReservationTime()
                )).toList();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable long id) {
        reservationRepository.delete(id);
    }
}
