package roomescape.repositiory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class ReservationH2Repository implements ReservationRepository {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public ReservationH2Repository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAll() {
        String query = "select id, name, date, time from RESERVATION";
        return jdbcTemplate.query(
                query, (rs, rowNum) -> new Reservation(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getObject("date", LocalDate.class),
                        rs.getObject("time", LocalTime.class)));
    }

    @Override
    public Reservation findById(Long id) {
        String query = "select id, name date, time from RESERVATION where id = ?";
        return jdbcTemplate.queryForObject(query, Reservation.class, id);
    }

    @Override
    public Reservation add(Reservation reservation) {
        return null;
    }

    @Override
    public void delete(Reservation reservation) {

    }
}
