package roomescape.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDateTime;
import roomescape.domain.ReservationTime;

public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<Reservation> rowMapper = (rs, rowNum) -> new Reservation(
            rs.getLong("reservation_id"),
            rs.getString("name"),
            ReservationDateTime.of(
                    rs.getDate("date").toLocalDate(),
                    new ReservationTime(rs.getLong("time_id"), rs.getTime("time_value").toLocalTime())
            )
    );

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingColumns("name", "date", "time_id")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Reservation> findAll() {
        String sql = """
                SELECT 
                    r.id as reservation_id, 
                    r.name,
                    r.date, 
                    rt.id AS time_id, 
                    rt.start_at AS time_value
                FROM reservation AS r
                JOIN reservation_time AS rt
                ON r.time_id = rt.id
                """;

        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Reservation add(Reservation reservation) {
        Map<String, Object> parameter = Map.of(
                "name", reservation.getName(),
                "date", reservation.getDate(),
                "time_id", reservation.getReservationTime().getId()
        );
        Long newId = simpleJdbcInsert.executeAndReturnKey(parameter).longValue();
        return reservation.withId(newId);
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        String sql = "SELECT * FROM reservation WHERE id = ?";
        Reservation reservation = jdbcTemplate.queryForObject(sql, rowMapper, id);
        return Optional.ofNullable(reservation);
    }
}
