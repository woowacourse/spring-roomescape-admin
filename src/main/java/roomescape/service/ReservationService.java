package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(final ReservationDao reservationDao, final ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationResponse> getReservations() {
        return reservationDao.findAll().stream()
                .map(ReservationResponse::toDto)
                .toList();
    }

    public ReservationResponse createReservation(final ReservationRequest reservationRequest) {
        ReservationTime reservationTime = reservationTimeDao.findById(reservationRequest.timeId());
        Reservation reservation = reservationRequest.toEntity(reservationTime);
        if (reservationDao.duplicateReservationByCustomer(reservation, reservationTime)) {
            throw new IllegalArgumentException("[ERROR] 같은 예약자가 같은 날짜, 시간에 중복으로 예약할 수 없습니다.");
        }
        Reservation newReservation = reservationDao.insert(reservation);
        return ReservationResponse.toDto(newReservation);
    }

    public boolean deleteReservationById(final Long id) {
        return reservationDao.deleteById(id);
    }
}
