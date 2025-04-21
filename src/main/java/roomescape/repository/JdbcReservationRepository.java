package roomescape.repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDateTime;
import roomescape.domain.ReservationTime;

public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingColumns("name", "date", "time_id")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Reservation> getReservations() {
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
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new Reservation(
                        rs.getLong("reservation_id"),
                        rs.getString("name"),
                        ReservationDateTime.of(
                                rs.getDate("date").toLocalDate(),
                                new ReservationTime(rs.getLong("time_id"), rs.getTime("time_value").toLocalTime())
                        )
                )
        );
    }

    @Override
    public Reservation add(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setDate(2, Date.valueOf(reservation.getDate()));
            ps.setLong(3, reservation.getReservationTime().getId());
            return ps;
        }, keyHolder);
        return new Reservation(keyHolder.getKey().longValue(), reservation);
    }

    public Reservation add2(Reservation reservation) {
        Map<String, Object> parameter = Map.of(
                "name", reservation.getName(),
                "date", reservation.getDate(),
                "time_id", reservation.getReservationTime().getId()
        );
        Long newId = simpleJdbcInsert.executeAndReturnKey(parameter).longValue();
        return new Reservation(newId, reservation);
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existReservation(Long id) {
        String sql = "SELECT COUNT(*) FROM reservation WHERE id = ?";
        Long count = jdbcTemplate.queryForObject(sql, Long.class, id);
        return count > 0;
    }
}
