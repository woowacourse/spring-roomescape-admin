package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    public List<ReservationResponseDto> getAllReservations() {
        return reservationDao.findAllReservations();
    }

    @PostMapping("/reservations")
    public ReservationResponseDto addReservation(@RequestBody final CreateReservationRequestDto reservationDto) {
        Reservation noIdReservation = reservationDto.toEntity();
        Long id = reservationDao.saveAndReturnId(noIdReservation);
        return new ReservationResponseDto(
                id,
                noIdReservation.name(),
                noIdReservation.date(),
                noIdReservation.time());
    }

    @DeleteMapping("/reservations/{id}")
    public void deleteReservation(@PathVariable("id") final Long id) {
        reservationDao.deleteById(id);
    }
}
