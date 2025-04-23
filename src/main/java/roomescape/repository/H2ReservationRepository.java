package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;

@Repository
public class H2ReservationRepository implements ReservationRepository {
    private JdbcTemplate jdbcTemplate;

    public H2ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        Reservation reservation = new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                LocalDate.parse(resultSet.getString("date")),
                LocalTime.parse(resultSet.getString("time"))
        );
        return reservation;
    };

    @Override
    public List<Reservation> findAll() {
        String sql = "select id, name, date, time from reservation";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    @Override
    public Reservation add(final Reservation reservation) {
        return null;
    }

    @Override
    public void removeById(final Long id) {
    }
}
