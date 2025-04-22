package roomescape.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.model.Reservation;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query("select * from reservation", (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getObject("date", LocalDate.class),
                resultSet.getObject("time", LocalTime.class)
        ));
    }

    public Reservation insertReservation(ReservationRequest request) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", request.name());
        parameters.put("date", request.date());
        parameters.put("time", request.time());
        Number number = simpleJdbcInsert.executeAndReturnKey(parameters);
        return new Reservation(number.longValue(), request.name(), request.date(), request.time());
    }

    public boolean deleteReservationById(long id) {
        String sql = "delete from reservation where id = ?";
        int updated = jdbcTemplate.update(sql, id);
        return updated != 0;
    }
}
