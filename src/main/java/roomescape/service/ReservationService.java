package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.exception.InUseTimeException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.command.ReservationCreateCommand;
import roomescape.service.command.ReservationTimeCreateCommand;

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
    public Reservation createReservation(
            ReservationCreateCommand createCommand
    ) {
        ReservationTime time = timeRepository.findById(createCommand.timeId());
        Reservation reservation = Reservation.create(
                createCommand.name(),
                createCommand.date(),
                time
        );

        return reservationRepository.create(reservation);
    }

    @Transactional
    public ReservationTime createTime(
            ReservationTimeCreateCommand createCommand
    ) {
        ReservationTime reservationTime = ReservationTime.create(createCommand.startAt());

        return timeRepository.create(reservationTime);
    }

    @Transactional
    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    @Transactional
    public List<ReservationTime> findAllTimes() {
        return timeRepository.findAll();
    }

    @Transactional
    public void deleteReservation(long reservationId) {
        reservationRepository.delete(reservationId);
    }

    @Transactional
    public void deleteTime(long timeId) {
        if (reservationRepository.existsTimeId(timeId)) {
            throw new InUseTimeException("사용 중인 예약이 없는 시간만 제거할 수 있습니다.");
        }

        timeRepository.delete(timeId);
    }
}
