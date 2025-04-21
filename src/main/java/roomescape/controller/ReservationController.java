package roomescape.controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.QueryingDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.dao.UpdatingDao;
import roomescape.domain.Person;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;

@RestController
public class ReservationController {

    private final QueryingDao queryingDao;
    private final UpdatingDao updatingDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationController(QueryingDao queryingDao, UpdatingDao updatingDao,
        ReservationTimeDao reservationTimeDao) {
        this.queryingDao = queryingDao;
        this.updatingDao = updatingDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    @GetMapping("/reservations")
    public List<ReservationResponseDto> readReservations() {
        return queryingDao.findAllReservation().stream()
            .map(ReservationResponseDto::from)
            .toList();
    }

    @PostMapping("/reservations")
    public ReservationResponseDto createReservations(
        @RequestBody ReservationRequestDto reservationRequestDto) {
        Person person = new Person(reservationRequestDto.name());
        LocalDate date = LocalDate.parse(reservationRequestDto.date());
        ReservationTime reservationTime = reservationTimeDao.findById(
            reservationRequestDto.timeId());
        Reservation reservation = new Reservation(person, date, reservationTime);
        updatingDao.saveReservation(reservation);
        return ReservationResponseDto.from(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public void deleteReservation(@PathVariable(name = "id") Long id) {
        updatingDao.deleteReservation(id);
    }
}
