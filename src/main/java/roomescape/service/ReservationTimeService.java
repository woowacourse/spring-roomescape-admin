package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.command.ReservationTimeCreateCommand;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(
            ReservationTimeRepository reservationTimeRepository
    ) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTime create(
        ReservationTimeCreateCommand createCommand
    ) {
        ReservationTime reservationTime = ReservationTime.create(createCommand.startAt());

        return reservationTimeRepository.create(reservationTime);
    }

    public List<ReservationTime> findAll() {
        return reservationTimeRepository.findAll();
    }

    public void delete(long id) {
        reservationTimeRepository.delete(id);
    }
}
