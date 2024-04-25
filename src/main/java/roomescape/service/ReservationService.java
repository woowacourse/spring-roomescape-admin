package roomescape.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.TimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationCreationRequest;
import roomescape.domain.ReservationTime;
import roomescape.service.dto.ReservationCreationDto;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;
    private final TimeDao timeDao;

    public ReservationService(ReservationDao reservationDao, TimeDao h2TimeDao) {
        this.reservationDao = reservationDao;
        this.timeDao = h2TimeDao;
    }

    public List<Reservation> findAllReservations() {
        return reservationDao.findAll();
    }

    public Reservation createReservation(ReservationCreationRequest request) {
        ReservationTime reservationTime = timeDao.findById(request.getTimeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약 시간입니다."));


        LocalDateTime reservationDatetime = LocalDateTime.of(request.getDate(), reservationTime.getStartTime());
        validateTimeSequence(reservationDatetime);

        // TODO: DB의 연관관계를 고려한 테이블 구조에 맞는 객체를 만들어서 넘겨줘야 하나?
        return reservationDao.add(ReservationCreationDto.from(request, reservationTime));
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
