package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeRepository;
import roomescape.domain.ReservationTime;
import roomescape.exception.InvalidReservationException;

import java.util.List;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(final ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTime create(final ReservationTime reservationTime) {
        return reservationTimeRepository.save(reservationTime);
    }

    public List<ReservationTime> findAll() {
        return reservationTimeRepository.findAll();
    }

    public void deleteById(final long id) {
        reservationTimeRepository.deleteById(id);
    }

    public ReservationTime findById(final long id) {
        if (reservationTimeRepository.existsById(id)) {
            return reservationTimeRepository.findById(id);
        }
        throw new InvalidReservationException("존재하지 않는 예약 시간입니다. id: " + id);
    }
}
