package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.controller.request.CreateReservationRequest;
import roomescape.controller.response.ReservationResponse;
import roomescape.controller.response.ReservationTimeResponse;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.persistence.ReservationDao;
import roomescape.persistence.ReservationTimeDao;

@Service
@Transactional
public class ReservationService {

    private final ReservationTimeDao reservationTImeDao;
    private final ReservationDao reservationDao;

    public ReservationService(ReservationTimeDao reservationTImeDao,
                              ReservationDao reservationDao) {
        this.reservationTImeDao = reservationTImeDao;
        this.reservationDao = reservationDao;
    }

    public Long create(CreateReservationRequest createReservationRequest) {
        ReservationTime reservationTime = reservationTImeDao.findById(createReservationRequest.timeId())
                .orElseThrow(() -> new IllegalArgumentException(
                        createReservationRequest.timeId() + "에 해당하는 reservation_time 튜플이 없습니다."));
        return reservationDao.create(
                new Reservation(
                        createReservationRequest.name(),
                        createReservationRequest.date(),
                        reservationTime));
    }

    public void deleteById(Long reservationId) {
        reservationDao.deleteById(reservationId);
    }

    public List<ReservationResponse> findAll() {
        List<Reservation> reservations = reservationDao.findAll();
        return reservations.stream()
                .map(this::toReservationResponse)
                .toList();
    }

    public ReservationResponse findById(Long reservationId) {
        Reservation reservation = reservationDao.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException(reservationId + "에 해당하는 reservation_time 튜플이 없습니다."));
        return toReservationResponse(reservation);
    }

    private ReservationResponse toReservationResponse(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                new ReservationTimeResponse(
                        reservation.getTime().id(),
                        reservation.getTime().startAt()));
    }
}
