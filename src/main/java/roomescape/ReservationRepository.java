package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAll() {
        String sql = "SELECT id, name, date, time FROM reservation";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new Reservation(
                rs.getLong("id"),
                rs.getString("name"),
                LocalDate.parse(rs.getString("date")),
                LocalTime.parse(rs.getString("time"))
        ));
    }

    public Reservation findById(Long id) {
        String sql = "SELECT id, name, date, time FROM reservation WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, (rs, ronNum) -> new Reservation(
                rs.getLong("id"),
                rs.getString("name"),
                LocalDate.parse(rs.getString("date")),
                LocalTime.parse(rs.getString("time"))
        ), id);
    }

    public Reservation save(Reservation reservation) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", reservation.getName());
        parameters.put("date", reservation.getDate().toString());
        parameters.put("time", reservation.getTime().toString());

        Number key = simpleJdbcInsert.executeAndReturnKey(parameters);

        return new Reservation(
                key.longValue(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
