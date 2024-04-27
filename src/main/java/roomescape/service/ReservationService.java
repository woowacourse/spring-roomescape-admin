package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationDao;
import roomescape.domain.ReservationTimeDao;
import roomescape.domain.dto.ReservationRequest;
import roomescape.domain.dto.ReservationResponse;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationResponse> findAll() {
        List<Reservation> reservations = reservationDao.findAll();
        return reservations.stream()
                .map(ReservationResponse::of)
                .toList();
    }

    public ReservationResponse save(ReservationRequest reservationRequest) {
        if (reservationRequest.name() == null || reservationRequest.name().isBlank()) {
            throw new IllegalArgumentException("이름이 입력되지 않았습니다. 이름을 입력해주세요.");
        }
        if (reservationRequest.date() == null) {
            throw new IllegalArgumentException("날짜가 입력되지 않았습니다. 날짜를 입력해주세요.");
        }
        ReservationTime reservationTime = reservationTimeDao.findById(reservationRequest.timeId());
        Reservation reservation = reservationRequest.toReservation(reservationTime);
        long id = reservationDao.save(reservation);
        return findById(id);
    }

    private ReservationResponse findById(long id) {
        Reservation reservation = reservationDao.findById(id);
        return ReservationResponse.of(reservation);
    }

    public void deleteById(long id) {
        int isDelete = reservationDao.deleteById(id);
        if (isDelete < 1) {
            throw new IllegalArgumentException("해당 id는 존재하지 않습니다. id = %d".formatted(id));
        }
    }

    public void deleteAll() {
        reservationTimeDao.deleteAll();
        reservationDao.deleteAll();
    }
}
