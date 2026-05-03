package roomescape.service;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.service.dto.ReservationCreateCommand;

@Service
@Transactional(readOnly = true)
public class ReservationService {
    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;
    private final Clock clock;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao, Clock clock) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
        this.clock = clock;
    }

    public List<Reservation> findAllReservations() {
        return reservationDao.findAll();
    }

    @Transactional
    public Reservation createReservation(ReservationCreateCommand command) {
        validateReservationDate(command.date());

        ReservationTime time;
        try {
            time = reservationTimeDao.findById(command.timeId());
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("존재하지 않는 예약 시간입니다.");
        }

        Reservation reservation = new Reservation(
                null,
                command.name(),
                command.date(),
                time
        );

        Long generatedId = reservationDao.save(reservation);
        reservation.setId(generatedId);
        return reservation;
    }

    @Transactional
    public void deleteReservation(Long id) {
        int affectedRows = reservationDao.deleteById(id);
        if (affectedRows == 0) {
            throw new IllegalArgumentException("이미 삭제되었거나 존재하지 않는 예약입니다.");
        }
    }

    private void validateReservationDate(LocalDate targetDate) {
        LocalDate currentDate = LocalDate.now(clock);
        if (targetDate.isBefore(currentDate)) {
            throw new IllegalArgumentException("과거 날짜는 예약할 수 없습니다.");
        }
    }
}
