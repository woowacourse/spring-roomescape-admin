package roomescape.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class ReservationDaoImpl implements ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAll() {
        final String sql = "select * from reservation";
        List<Reservation> query = jdbcTemplate.query(sql, (resultSet, rowNumber) -> {
            long id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            LocalDate date = LocalDate.parse(resultSet.getString("date"));
            LocalTime time = LocalTime.parse(resultSet.getString("time"));
            return new Reservation(id, name, date, time);
        });
        return query;
    }
}
