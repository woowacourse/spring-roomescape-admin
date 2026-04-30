package roomescape.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

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
        String sql = """
                SELECT r.id as reservation_id,
                       r.name,
                       r.date,
                       t.id as time_id,
                       t.start_at
                FROM reservation as r
                INNER JOIN reservation_time as t ON r.time_id = t.id
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new Reservation(
                rs.getLong("reservation_id"),
                rs.getString("name"),
                LocalDate.parse(rs.getString("date")),
                new ReservationTime(
                        rs.getLong("time_id"),
                        LocalTime.parse(rs.getString("start_at")))
        ));
    }

    public Reservation findById(Long id) {
        String sql = """
                SELECT r.id as reservation_id,
                       r.name,
                       r.date,
                       t.id as time_id,
                       t.start_at
                FROM reservation as r
                INNER JOIN reservation_time as t ON r.time_id = t.id
                WHERE r.id = ?
                """;

        return jdbcTemplate.queryForObject(sql, (rs, ronNum) -> new Reservation(
                rs.getLong("reservation_id"),
                rs.getString("name"),
                LocalDate.parse(rs.getString("date")),
                new ReservationTime(
                        rs.getLong("time_id"),
                        LocalTime.parse(rs.getString("start_at")))
        ), id);
    }

    public Reservation save(Reservation reservation) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", reservation.getName());
        parameters.put("date", reservation.getDate().toString());
        parameters.put("time_id", reservation.getTime().getId());

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
