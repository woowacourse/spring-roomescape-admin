package roomescape.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.QueryingDao;
import roomescape.dao.UpdatingDao;
import roomescape.domain.Person;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDateTime;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;

@RestController
public class ReservationController {

    private final QueryingDao queryingDao;
    private final UpdatingDao updatingDao;

    public ReservationController(QueryingDao queryingDao, UpdatingDao updatingDao) {
        this.queryingDao = queryingDao;
        this.updatingDao = updatingDao;
    }

    @GetMapping("/reservations")
    public List<ReservationResponseDto> readReservations() {
        return queryingDao.findAllReservation().stream()
            .map(ReservationResponseDto::from)
            .toList();
    }

    @PostMapping("/reservations")
    public Reservation createReservations(
        @RequestBody ReservationRequestDto reservationRequestDto) {
        Person person = new Person(reservationRequestDto.name());
        ReservationDateTime reservationDateTime = new ReservationDateTime(
            LocalDateTime.of(reservationRequestDto.date(), reservationRequestDto.time()));
        Reservation reservation = new Reservation(person, reservationDateTime);
        updatingDao.saveReservation(reservation);
        return reservation;
    }

    @DeleteMapping("/reservations/{id}")
    public void deleteReservation(@PathVariable(name = "id") Long id) {
        updatingDao.deleteReservation(id);
    }
}
