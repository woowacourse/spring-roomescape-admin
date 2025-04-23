package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationRepositoryImpl(final JdbcTemplate jdbcTemplate, final DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
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

    @Override
    public Reservation insert(final String name, final LocalDate date, final LocalTime time) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", name);
        parameters.put("date", date);
        parameters.put("time", time);
        long id = (long) simpleJdbcInsert.executeAndReturnKey(parameters);
        return new Reservation(id, name, date, time);
    }

    @Override
    public void delete(final long id) {
        final String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
