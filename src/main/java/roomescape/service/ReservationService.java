package roomescape.service;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationResponse> getReservations() {
        List<Reservation> reservations = reservationDao.getReservations();

        return reservations.stream()
                .map(ReservationResponse::of)
                .toList();
    }

    public ReservationResponse createReservation(ReservationRequest request) {

        ReservationTime reservationTime = reservationTimeDao.findById(request.timeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약 시간입니다."));

        Reservation reservation = new Reservation( request.name(), request.date(), reservationTime);

        Long reservationId = reservationDao.createReservation(reservation);

        Reservation saved = reservation.withId(reservationId);
        return ReservationResponse.of(saved);
    }

    public void deleteReservation(long id) {
        try {
            reservationDao.deleteReservation(id);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("삭제할 예약이 존재하지 않습니다.");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }

    }
}
