package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationCreateRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.dto.ReservationTimeResponseDto;
import roomescape.repository.ReservationJdbcRepository;

import java.util.List;

@RestController
public class ReservationController {

    private final ReservationJdbcRepository reservationDao;

    @Autowired
    public ReservationController(ReservationJdbcRepository reservationDao) {
        this.reservationDao = reservationDao;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponseDto>> getAllReservations() {
        List<ReservationResponseDto> allReservations = reservationDao.findAllReservations();
        return ResponseEntity.ok(allReservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponseDto> addReservation(@RequestBody final ReservationCreateRequestDto requestDto) {
        try {
            Long id = reservationDao.saveAndReturnId(requestDto.name(), requestDto.date(), requestDto.timeId());
            Reservation newReservation = reservationDao.findById(id);
            ReservationTime reservationTime = newReservation.reservationTime();

            ReservationTimeResponseDto timeResponseDto = new ReservationTimeResponseDto(reservationTime.id(), reservationTime.startAt());
            ReservationResponseDto responseDto = new ReservationResponseDto(
                    newReservation.id(), newReservation.name(), newReservation.date(), timeResponseDto);
            return ResponseEntity.ok(responseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") final Long id) {
        try {
            reservationDao.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
