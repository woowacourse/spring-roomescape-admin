package roomescape.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.domain.Person;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.domain.ReservationTimes;
import roomescape.domain.Reservations;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final Reservations reservations;
    private final ReservationTimes reservationTimes;

    public ReservationService(ReservationDao reservationDao, Reservations reservations,
        ReservationTimes reservationTimes) {
        this.reservationDao = reservationDao;
        this.reservations = reservations;
        this.reservationTimes = reservationTimes;
    }

    public List<ReservationResponseDto> getAllReservations() {
        return reservations.getReservations().stream()
            .map(ReservationResponseDto::from)
            .toList();
    }

    public ReservationResponseDto saveReservation(ReservationRequestDto reservationRequestDto) {
        Person person = new Person(reservationRequestDto.name());
        LocalDate date = LocalDate.parse(reservationRequestDto.date());

        ReservationTime reservationTime = reservationTimes.findById(
            reservationRequestDto.timeId());
        //ReservationTime reservationTime = reservationTimeDao.findById(
        //    reservationRequestDto.timeId());
        Reservation reservation = new Reservation(person, date, reservationTime);
        reservationDao.saveReservation(reservation);
        reservations.save(reservation);
        return ReservationResponseDto.from(reservation);
    }

    public void deleteReservation(Long id) {
        reservationDao.deleteReservation(id);
        reservations.deleteById(id);
    }
}
