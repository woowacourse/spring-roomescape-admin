package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.controller.dto.ReservationCreateRequest;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationDao;

import java.util.List;

@Service
public class ReservationService {

    private static final String RESERVATION_NOT_FOUND = "존재하지 않는 예약입니다.";

    private final ReservationDao reservationDao;
    private final ReservationTimeService reservationTimeService;

    @Autowired
    public ReservationService(ReservationDao reservationDao, ReservationTimeService reservationTimeService) {
        this.reservationDao = reservationDao;
        this.reservationTimeService = reservationTimeService;
    }

    public List<Reservation> readReservations() {
        return reservationDao.findAll();
    }

    public Reservation readReservation(Long id) {
        return reservationDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(RESERVATION_NOT_FOUND));
    }

    public Reservation createReservation(ReservationCreateRequest request) {
        ReservationTime reservationTime = reservationTimeService.readReservationTime(request.timeId());
        Reservation reservation = request.toReservation(reservationTime);

        return reservationDao.save(reservation);
    }

    public void deleteReservation(Long id) {
        reservationDao.deleteById(id);
    }
}
