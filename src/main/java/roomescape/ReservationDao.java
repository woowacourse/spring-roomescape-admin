package roomescape;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private static final RowMapper<Reservation> ROW_MAPPER = (resultSet, rowNum) ->
            new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getDate("date").toLocalDate(),
                    resultSet.getTime("time").toLocalTime()
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
        parameters.put("time", reservation.getTime());

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
                SELECT id, 
                       name, 
                       date,
                       time 
                FROM reservation""";

        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    public Reservation findById(Long reservationId) {
        String sql = """
                SELECT id, 
                       name, 
                       date,
                       time 
                FROM reservation
                WHERE id = ?""";

        return jdbcTemplate.queryForObject(sql, ROW_MAPPER, reservationId);
    }

    public void delete(Reservation foundReservation) {
        String sql = """
                DELETE FROM reservation
                WHERE id = ?""";

        jdbcTemplate.update(sql, foundReservation.getId());
    }
}
