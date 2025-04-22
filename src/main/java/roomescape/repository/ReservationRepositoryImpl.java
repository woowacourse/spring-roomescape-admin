package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository{

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation findById(final long id) {
        return null;
    }

    @Override
    public List<Reservation> findAll() {
        return null;
    }

    @Override
    public Reservation save(final Reservation reservation) {

        return reservation;
    }

    @Override
    public void deleteById(final long id) {
    }

    @Override
    public boolean selectByDateAndTime(LocalDate date, LocalTime time) {
        return false;

    }
}
