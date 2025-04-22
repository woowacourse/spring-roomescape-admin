package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;

@Repository
public class H2ReservationRepository implements ReservationRepository{

    private final JdbcTemplate jdbcTemplate;

    public H2ReservationRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation findById(final long id) {
        final String sql = "SELECT * FROM reservation WHERE id = ?";
        return jdbcTemplate.queryForObject(sql,
                (resultSet, rowNum) ->{
                    Reservation reservation = new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                            resultSet.getDate("date").toLocalDate(),
                            resultSet.getTime("time").toLocalTime()
                    );
                    return reservation;
                }, id);

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
