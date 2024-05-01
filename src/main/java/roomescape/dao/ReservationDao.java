package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<Reservation> rowMapper = (rs, rowNum) -> {
        Long timeId = rs.getLong("time_id");
        String startTime = rs.getString("time_value");
        ReservationTime reservationTime = new ReservationTime(timeId, startTime);

        return new Reservation(
                rs.getLong("reservation_id"),
                rs.getString("reservation_name"),
                rs.getObject("reservation_date", LocalDate.class),
                reservationTime
        );
    };

    public ReservationDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(Reservation reservation) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", reservation.getName());
        parameters.put("date", reservation.getDate());
        parameters.put("time_id", reservation.getTime().getId());
        Number key = simpleJdbcInsert.executeAndReturnKey(parameters);
        return key.longValue();
    }

    public List<Reservation> readAll() {
        String query = "SELECT " +
                "    r.id as reservation_id, " +
                "    r.name as reservation_name, " +
                "    r.date as reservation_date, " +
                "    t.id as time_id, " +
                "    t.start_at as time_value " +
                "FROM reservation as r " +
                "inner join reservation_time as t " +
                "on r.time_id = t.id";

        return jdbcTemplate.query(query, rowMapper);
    }

    public void delete(Long id) {
        String query = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(query, id);
    }
}
