package roomescape.reservation.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.ReservationTime;
import roomescape.reservation.domain.Reservation;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private static final RowMapper<Reservation> ROW_MAPPER = (resultSet, rowNum) ->
            new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getDate("date").toLocalDate(),
                    new ReservationTime(
                            resultSet.getLong("id"),
                            resultSet.getTime("start_at").toLocalTime()
                    )
            );

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public Reservation save(Reservation reservation) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", reservation.getName());
        parameters.put("date", reservation.getDate());
        parameters.put("time_id", reservation.getTime().getId());

        Number id = simpleJdbcInsert.executeAndReturnKey(parameters);

        return new Reservation(
                id.longValue(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    public List<Reservation> findAllReservations() {
        String sql = """
                SELECT r.id, 
                       r.name, 
                       r.date,
                       rt.id ,
                       rt.start_at
                FROM reservation AS r
                INNER JOIN reservation_time AS rt 
                ON r.time_id = rt.id""";

        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    public Optional<Reservation> findById(Long reservationId) {
        String sql = """
                SELECT r.id, 
                       r.name, 
                       r.date,
                       rt.id ,
                       rt.start_at
                FROM reservation AS r
                INNER JOIN reservation_time AS rt 
                ON r.time_id = rt.id
                WHERE r.id = ?""";

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, ROW_MAPPER, reservationId));
    }

    public void delete(Reservation reservation) {
        String sql = """
                DELETE FROM reservation
                WHERE id = ?""";

        jdbcTemplate.update(sql, reservation.getId());
    }
}
