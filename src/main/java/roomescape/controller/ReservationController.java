package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.reservation.ReservationRequestDto;
import roomescape.dto.reservation.ReservationResponseDto;
import roomescape.dto.reservationTime.ReservationTimeRequesetDto;
import roomescape.dto.reservationTime.ReservationTimeResponseDto;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ReservationController {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationController(ReservationRepository reservationRepository, ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @GetMapping("/reservations")
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationResponseDto> getReservations() {
        List<ReservationResponseDto> responseDtos = new ArrayList<>();
        List<Reservation> reservations = reservationRepository.findAll();

        for (Reservation reservation : reservations) {
            responseDtos.add(ReservationResponseDto.from(reservation));
        }

        return responseDtos;
    }

    @PostMapping("/reservations")
    public ReservationResponseDto addReservation(@RequestBody ReservationRequestDto requestDto) {
        Reservation reservation = new Reservation(null, requestDto.name(), requestDto.date(), requestDto.time());

        reservationRepository.createReservation(reservation);

        return ReservationResponseDto.from(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteReservation(@PathVariable("id") Long id) {
        reservationRepository.deleteById(id);
    }

    @PostMapping("/times")
    public ReservationTimeResponseDto addReservationTime(@RequestBody ReservationTimeRequesetDto requestDto) {
        ReservationTime reservationTime = reservationTimeRepository.createReservationTime(new ReservationTime(null, requestDto.startAt()));

        return ReservationTimeResponseDto.from(reservationTime);
    }
}
