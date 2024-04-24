package roomescape.admin.reservation.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.admin.reservation.controller.dto.request.ReservationTimeRequest;
import roomescape.admin.reservation.entity.ReservationTime;
import roomescape.admin.reservation.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    @Autowired
    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTime> findAll() {
        return reservationTimeRepository.findAll();
    }

    public ReservationTime create(ReservationTimeRequest reservationTimeRequest) {
        return reservationTimeRepository.save(new ReservationTime(reservationTimeRequest.startAt()));
    }

    public int delete(Long id) {
        return reservationTimeRepository.delete(id);
    }
}
