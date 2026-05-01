package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.exception.DeleteFailureException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.command.ReservationCreateCommand;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository timeRepository;

    public ReservationService(
            ReservationRepository reservationRepository,
            ReservationTimeRepository timeRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    @Transactional
    public Reservation create(
            ReservationCreateCommand createCommand
    ) {
        ReservationTime time = timeRepository.findById(createCommand.timeId());
        Reservation reservation = Reservation.create(
                createCommand.name(),
                createCommand.date(),
                time
        );

        return reservationRepository.persist(reservation);
    }

    @Transactional
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Transactional
    public void delete(long reservationId) {
        boolean deleted = reservationRepository.delete(reservationId);

        if (!deleted) {
            throw new DeleteFailureException("예약 삭제에 실패했습니다. reservationId = " + reservationId);
        }
    }
}
