package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class H2ReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Reservation> rowMapper = (rs, rowNum) -> {
        Long id = rs.getLong("id");
        String name = rs.getString("name");
        LocalDate date = rs.getObject("date", LocalDate.class);
        LocalTime time = rs.getObject("time", LocalTime.class);

        return new Reservation(id, name, date, time);
    };

    public H2ReservationRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT * FROM reservation";

        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Reservation save(Reservation reservation) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
    }
}
