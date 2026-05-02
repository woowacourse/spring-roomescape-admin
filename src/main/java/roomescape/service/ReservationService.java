package roomescape.service;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
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


        Long reservationId = reservationDao.createReservation(request);
        Reservation reservation = new Reservation(reservationId, request.name(), request.date(), reservationTime);

        return ReservationResponse.of(reservation);
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
