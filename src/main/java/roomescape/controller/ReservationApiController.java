package roomescape.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationTimeResponseDto;
import roomescape.model.ReservationDate;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.model.Reservation;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.repository.ReservationTimeRepository;

@RestController
@RequestMapping("/reservations")
public final class ReservationApiController {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationApiController(ReservationRepository reservationRepository,
                                    ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @GetMapping
    public List<ReservationResponseDto> reservations() {
        return reservationRepository.findAll().stream()
                .map(reservation -> new ReservationResponseDto(
                        reservation.getId(),
                        reservation.getName(),
                        reservation.getDate().getStartDate(),
                        new ReservationTimeResponseDto(reservation.getTime().getId(),
                                reservation.getTime().getStartTime())))
                .toList();
    }

    @PostMapping
    public ReservationResponseDto reserve(@Valid @RequestBody ReservationRequestDto reservationRequestDto) {
        ReservationDate reservationDate = new ReservationDate(reservationRequestDto.date());
        ReservationTime reservationTime = reservationTimeRepository.findById(reservationRequestDto.timeId());
        Reservation reservation = reservationRepository.add(new Reservation(
                reservationRequestDto.name(),
                reservationDate,
                reservationTime
        ));
        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate().getStartDate(),
                new ReservationTimeResponseDto(reservationTime.getId(),
                        reservationTime.getStartTime()));
    }

    @DeleteMapping("/{id}")
    public void cancel(@PathVariable int id) {
        reservationRepository.removeById(id);
    }
}
