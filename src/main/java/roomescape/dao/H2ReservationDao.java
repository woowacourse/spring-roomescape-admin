package roomescape.dao;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.service.dto.ReservationCreationDto;

@Repository
public class H2ReservationDao implements ReservationDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public H2ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id")
                .usingColumns("name", "date", "time");
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT id, name, `date`, `time` FROM reservation";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Reservation(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("date"),
                rs.getString("time")
        ));
    }

    @Override
    public Reservation add(ReservationCreationDto request) {
        Map<String, Object> parameters = Map.of(
                "name", request.name(),
                "date", request.date(),
                "time", request.time()
        );
        Number key = simpleJdbcInsert.executeAndReturnKey(parameters);
        return new Reservation(
                key.longValue(), request.name(),
                request.date(), request.time()
        );
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM reservation";
        jdbcTemplate.update(sql);
    }

    @Override
    public boolean isExist(Long id) {
        String sql = "SELECT * FROM reservation WHERE id = ? LIMIT 1";
        List<Reservation> reservations = jdbcTemplate.query(sql, (rs, rowNum) -> new Reservation(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("date"),
                        rs.getString("time")
                ), id);
        return !reservations.isEmpty();
    }
}
