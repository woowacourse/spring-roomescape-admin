package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.reservation.ReservationQueryingDao;
import roomescape.reservation.Reservation;
import roomescape.reservation.ReservationRequest;
import roomescape.reservation.ReservationResponse;
import roomescape.reservation.ReservationUpdatingDao;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationQueryingDao reservationQueryingDao;
    private final ReservationUpdatingDao reservationUpdatingDao;

    public ReservationService(ReservationQueryingDao reservationQueryingDao, ReservationUpdatingDao reservationUpdatingDao) {
        this.reservationQueryingDao = reservationQueryingDao;
        this.reservationUpdatingDao = reservationUpdatingDao;
    }

    public List<ReservationResponse> read() {
        List<Reservation> reservations = reservationQueryingDao.findAllReservations();
         return reservations.stream()
                .map(reservation -> new ReservationResponse(
                        reservation.getId(),
                        reservation.getName(),
                        reservation.getDate(),
                        reservation.getTime().getStartAt()
                ))
                .toList();
    }

    public Reservation create(ReservationRequest reservationReq) {
        Long generatedId = reservationUpdatingDao.insert(reservationReq);
        return reservationQueryingDao.findReservationById(generatedId);
    }

    public void update(ReservationRequest newReservationReq, Long id) {
        reservationUpdatingDao.save(id, newReservationReq);
    }

    public void delete(Long id) {
        int count = reservationUpdatingDao.delete(id);

        if (count == 0) {
            throw new RuntimeException("삭제하려는 예약을 찾을 수 없습니다.");
        }
    }
}
