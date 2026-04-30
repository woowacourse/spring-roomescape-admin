package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.reservationtime.ReservationTime;
import roomescape.reservationtime.TimeQueryingDAO;
import roomescape.reservationtime.TimeUpdatingDAO;

import java.util.List;

@Service
public class ReservationTimeService {

    private final TimeQueryingDAO timeQueryingDAO;
    private final TimeUpdatingDAO timeUpdatingDAO;

    public ReservationTimeService(TimeQueryingDAO timeQueryingDAO, TimeUpdatingDAO timeUpdatingDAO) {
        this.timeQueryingDAO = timeQueryingDAO;
        this.timeUpdatingDAO = timeUpdatingDAO;
    }

    public List<ReservationTime> read() {
        return timeQueryingDAO.findAllReservationTime();
    }

    public ReservationTime create(ReservationTime reservationTime) {
        Long generatedId = timeUpdatingDAO.insertWithKeyHolder(reservationTime);
        return timeQueryingDAO.findReservationTimeById(generatedId);
    }

    public void update(ReservationTime newReservationTime, Long id) {
        timeUpdatingDAO.save(id, newReservationTime);
    }

    public void delete(Long id) {
        int delete = timeUpdatingDAO.delete(id);

        if (delete == 0) {
            throw new RuntimeException("삭제하려는 예약 시간을 찾을 수 없습니다.");
        }
    }
}
