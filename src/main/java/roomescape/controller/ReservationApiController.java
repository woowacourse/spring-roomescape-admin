package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dao.ReservationDao;
import roomescape.dto.CreateReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.model.Reservation;

import java.util.List;

@RestController
public class ReservationApiController {

    private final ReservationDao reservationDao;

    @Autowired
    public ReservationApiController(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponseDto>> getAllReservations() {
        List<ReservationResponseDto> allReservations = reservationDao.findAllReservations();
        return ResponseEntity.ok(allReservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponseDto> addReservation(@RequestBody final CreateReservationRequestDto reservationDto) {
        Reservation noIdReservation = reservationDto.toEntity();
        Long id = reservationDao.saveAndReturnId(noIdReservation);
        ReservationResponseDto responseDto = new ReservationResponseDto(id, noIdReservation.name(), noIdReservation.date(), noIdReservation.time());
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") final Long id) {
        try {
            reservationDao.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
