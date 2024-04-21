package roomescape.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.dto.ReservationTimeDto;
import roomescape.entity.Reservation;

@Service
public class ReservationService {

    @Autowired
    private ReservationDao reservationDao;

    public List<ReservationResponseDto> findReservations() {
        List<Reservation> reservations = reservationDao.findAllReservations();
        return reservations.stream()
                .map(ReservationResponseDto::from)
                .toList();
    }

    public ReservationResponseDto addReservation(ReservationRequestDto reservationDto) {
        long id = reservationDao.save(reservationDto);
        Reservation reservation = reservationDao.findById(id); // TODO: save + findById ???
        return new ReservationResponseDto(
                id,
                reservation.getName(),
                reservation.getDate(),
                ReservationTimeDto.from(reservation.getTime())
        );
    }

    public void deleteReservation(long id) {
        reservationDao.delete(id);
    }
}
