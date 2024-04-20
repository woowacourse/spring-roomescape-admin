package roomescape.persistence;

import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationDbRepository implements ReservationRepository {
    private final ReservationDao reservationDao;

    public ReservationDbRepository(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @Override
    public Reservation save(Reservation reservation) {
        Long id = reservationDao.insert(reservation);
        return new Reservation(id, reservation);
    }

    @Override
    public List<Reservation> findAllByDateAndTime(LocalDate date, LocalTime time) {
        return reservationDao.selectAllByDateAndTime(date, time);
    }

    @Override
    public List<Reservation> findAll() {
        return reservationDao.selectAll();
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return Optional.ofNullable(reservationDao.selectById(id));
    }

    @Override
    public void deleteById(Long id) {
        reservationDao.deleteById(id);
    }
}
