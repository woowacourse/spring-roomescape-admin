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
            Long timeId = reservation.getTime().getId();
            ReservationTime time = reservationTimeRepository.findById(timeId);
            Reservation reservationIncludingTime = new Reservation(reservation, time);

            responseDtos.add(ReservationResponseDto.from(reservationIncludingTime));
        }

        return responseDtos;
    }

    @PostMapping("/reservations")
    @ResponseStatus(HttpStatus.OK)
    public ReservationResponseDto addReservation(@RequestBody ReservationRequestDto requestDto) {
        ReservationTime time = reservationTimeRepository.findById(requestDto.time_id());
        Long id = reservationRepository.createReservation(new Reservation(null, requestDto.name(), requestDto.date(), time));
        Reservation reservation = reservationRepository.findById(id);

        return ReservationResponseDto.from(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteReservation(@PathVariable("id") Long id) {
        reservationRepository.deleteById(id);
    }

    @GetMapping("/times")
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationTimeResponseDto> getReservationTimes() {
        List<ReservationTimeResponseDto> responseDtos = new ArrayList<>();
        List<ReservationTime> times = reservationTimeRepository.findAll();

        for (ReservationTime time : times) {
            responseDtos.add(ReservationTimeResponseDto.from(time));
        }

        return responseDtos;
    }

    @PostMapping("/times")
    @ResponseStatus(HttpStatus.OK)
    public ReservationTimeResponseDto addReservationTime(@RequestBody ReservationTimeRequesetDto requestDto) {
        ReservationTime reservationTime = reservationTimeRepository.createReservationTime(new ReservationTime(null, requestDto.startAt()));

        return ReservationTimeResponseDto.from(reservationTime);
    }

    @DeleteMapping("/times/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteReservationTime(@PathVariable("id") Long id) {
        reservationTimeRepository.deleteById(id);
    }
}
