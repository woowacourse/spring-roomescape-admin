package roomescape.admin.reservation.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.admin.reservation.entity.ReservationTime;
import roomescape.admin.reservation.service.exception.NoSuchDeleteIdException;
import roomescape.admin.reservation.service.port.ReservationTimeRepository;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTime> findAll() {
        return reservationTimeRepository.findAll();
    }

    public ReservationTime create(ReservationTime reservationTime) {
        return reservationTimeRepository.save(new ReservationTime(reservationTime.getStartAt()));
    }

    public void delete(Long id) {
        int rowCount = reservationTimeRepository.delete(id);
        if (rowCount == 0) {
            throw new NoSuchDeleteIdException();
        }
    }
}
