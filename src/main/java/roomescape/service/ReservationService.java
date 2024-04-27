package roomescape.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.controller.dto.ReservationAddRequest;
import roomescape.controller.dto.ReservationResponse;
import roomescape.dao.ReservationDao;
import roomescape.dao.TimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;
    private final TimeDao timeDao;

    public ReservationService(ReservationDao reservationDao, TimeDao h2TimeDao) {
        this.reservationDao = reservationDao;
        this.timeDao = h2TimeDao;
    }

    public List<ReservationResponse> findAllReservations() {
        return reservationDao.findAll().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse createReservation(ReservationAddRequest request) {
        ReservationTime reservationTime = timeDao.findById(request.timeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약 시간입니다."));
        LocalDateTime reservationDatetime = LocalDateTime.of(request.date(), reservationTime.getStartTime());
        validateTimeSequence(reservationDatetime);

        Reservation reservation = request.toEntity(reservationTime);
        return ReservationResponse.from(reservationDao.add(reservation));
    }

    private void validateTimeSequence(LocalDateTime reservationDatetime) {
        if (reservationDatetime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("과거의 시각은 예약할 수 없습니다.");
        }
    }

    public void cancelReservation(Long reservationId) {
        reservationDao.delete(reservationId);
    }

    public boolean checkReservationExist(Long reservationId) {
        return reservationDao.isExist(reservationId);
    }
}
