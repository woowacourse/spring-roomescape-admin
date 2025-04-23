package roomescape.admin.service;

import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import roomescape.admin.domain.ReservationTime;
import roomescape.admin.repository.time.ReservationTimeRepository;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(
            @Qualifier("h2ReservationTimeRepository") final ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public Long save(final LocalTime startAt) {
        final ReservationTime reservationTime = new ReservationTime(startAt);

        return reservationTimeRepository.save(reservationTime);
    }

    public ReservationTime getOneById(final Long id) {
        return reservationTimeRepository.getOneById(id);
    }

    public ReservationTime getOneByStartAt(final LocalTime startAt) {
        return reservationTimeRepository.getOneByStartAt(startAt);
    }

    public List<ReservationTime> findAll() {
        return reservationTimeRepository.findAll();
    }

    public void deleteById(final Long id) {
        final ReservationTime found = reservationTimeRepository.getOneById(id);

        reservationTimeRepository.delete(found);
    }
}
