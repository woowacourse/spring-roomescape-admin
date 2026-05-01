package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.ReservationTime;
import roomescape.exception.DeleteFailureException;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.command.ReservationTimeCreateCommand;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository repository;

    public ReservationTimeService(
            ReservationTimeRepository repository
    ) {
        this.repository = repository;
    }

    @Transactional
    public ReservationTime create(
            ReservationTimeCreateCommand createCommand
    ) {
        ReservationTime reservationTime = ReservationTime.create(createCommand.startAt());

        return repository.persist(reservationTime);
    }

    @Transactional
    public List<ReservationTime> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void delete(long timeId) {
        boolean deleted = repository.delete(timeId);

        if (!deleted) {
            throw new DeleteFailureException("시간 삭제에 실패했습니다.");
        }
    }
}
