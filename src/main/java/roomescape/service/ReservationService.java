package roomescape.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dao.ReservationDao;
import roomescape.dao.TimeDao;
import roomescape.dao.vo.ReservationRow;
import roomescape.dao.vo.ReservationRows;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.service.command.CreateReservationCommand;

import java.util.List;

@Service
@Transactional
public class ReservationService {
    private final ReservationDao reservationDao;
    private final TimeDao timeDao;

    public ReservationService(ReservationDao reservationDao, TimeDao timeDao) {
        this.reservationDao = reservationDao;
        this.timeDao = timeDao;
    }

    @Transactional(readOnly = true)
    public List<Reservation> findAll() {
        ReservationRows reservationRows = reservationDao.findAll();
        return reservationRows.toReservations();
    }

    public Reservation create(CreateReservationCommand command) {
        Time timeById = timeDao.findById(command.getTimeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 시간입니다.")).toTime();

        Reservation reservation = new Reservation(command.getName(), command.getDate(), timeById);
        Long id = reservationDao.insert(reservation);

        return reservationDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다."))
                .toReservation();
    }

    public void delete(Long id) {
        Reservation reservation = reservationDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다."))
                .toReservation();

        reservationDao.delete(reservation.getId());
    }
}
