package roomescape.reservation.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.mapper.ReservationMapper;
import roomescape.reservation.repository.dao.ReservationDao;
import roomescape.time.repository.dao.ReservationTimeDao;

@Repository
public class ReservationRepository {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationRepository(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<Reservation> findAll() {
        return reservationDao.selectAll().stream()
                .map(reservation ->
                        ReservationMapper.toReservation(reservation,
                                reservationTimeDao.findById(reservation.getTimeId()))
                ).toList();
    }

    public Reservation save(Reservation reservation) {
        Long id = reservationDao.insert(reservation);
        return reservation.withId(id);
    }

    public void delete(Long id) {
        int deletedCount = reservationDao.deleteById(id);

        if (deletedCount == 0) {
            throw new IllegalArgumentException("존재하지 않는 예약 번호입니다.");
        }
    }
}
