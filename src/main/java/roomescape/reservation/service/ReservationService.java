package roomescape.reservation.service;

import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import roomescape.reservation.dao.ReservationDao;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.time.dao.ReservationTimeDao;
import roomescape.time.domain.ReservationTime;
import roomescape.time.dto.TimeResponse;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(final ReservationDao reservationDao, final ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationResponse createReservation(final ReservationRequest reservationRequest) {
        try {
            ReservationTime reservationTime = reservationTimeDao.findReservationTimeById(reservationRequest.timeId());
            Reservation notSavedreservation = new Reservation(
                    null, reservationRequest.name(), reservationRequest.date(), reservationTime
            );
            Reservation savedReservation = reservationDao.insertReservation(notSavedreservation);
            return new ReservationResponse(
                    savedReservation.getId(),
                    savedReservation.getName(),
                    savedReservation.getDate(),
                    new TimeResponse(savedReservation.getTime().getId(), savedReservation.getTime().getStartAt())
            );
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("[ERROR] 요청받은 timeId가 존재하지 않습니다.");
        }
    }

    public void removeReservation(final long id) {
        reservationDao.removeReservation(id);
    }

    public List<ReservationResponse>  findAllReservations() {
        List<Reservation> reservations = reservationDao.findAllReservations();

        return reservations.stream()
                .map(reservation -> new ReservationResponse(
                        reservation.getId(),
                        reservation.getName(),
                        reservation.getDate(),
                        new TimeResponse(reservation.getTime().getId(), reservation.getTime().getStartAt())
                )).toList();
    }
}
